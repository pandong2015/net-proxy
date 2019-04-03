package tech.pcloud.proxy.network.server.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.service.CommandService;
import tech.pcloud.proxy.network.core.service.CommandServiceFactory;
import tech.pcloud.proxy.network.core.service.ServiceKey;
import tech.pcloud.proxy.network.core.service.impl.DefaultCommandServiceFactory;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;
import tech.pcloud.proxy.network.server.utils.ServerCache;
import tech.pcloud.proxy.network.server.utils.ServerProtocolHelper;

import java.util.Map;

/**
 * @ClassName ServerProtocolChannelHandler
 * @Author pandong
 * @Date 2019/2/13 11:07
 **/
@Slf4j
public class ServerProtocolChannelHandler extends SimpleChannelInboundHandler<ProtocolPackage.Protocol> {
    private CommandServiceFactory commandServiceFactory;

    public ServerProtocolChannelHandler() {
        this(new DefaultCommandServiceFactory("tech.pcloud.proxy.network.server"));
    }

    public ServerProtocolChannelHandler(CommandServiceFactory commandServiceFactory) {
        this.commandServiceFactory = commandServiceFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtocolPackage.Protocol msg) throws Exception {
        Operation operation = Operation.getOperation(msg.getOperation().getOperation());
        log.debug("server operation:{}, request type:{} ", operation.name(), msg.getOperation().getType().name());
        Map<String, String> headers = msg.getHeadersMap();
        ProtocolCommand command = ProtocolCommand.newInstance(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.COMMAND).set(command);
        ctx.channel().attr(NetworkModel.ChannelAttribute.HEADER).set(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.OPERATION).set(msg.getOperation());
        ServiceKey key = new ServiceKey(operation.getOperation(), msg.getOperation().getType(), command.getNodeType());
        CommandService commandService = commandServiceFactory.getCommandService(key);
        switch (operation) {
            case HEARTBEAT:
                log.debug("heartbeat request");
                ctx.channel().writeAndFlush(ServerProtocolHelper.createHeartbeatResponseProtocol());
                break;
            case NORMAL:
                String content = msg.getBody().toStringUtf8();
                commandService.execute(msg.getOperation(), command, ctx.channel(), content);
                break;
            case TRANSFER:
                long requestId = getRequestIdFromHeader(headers);
                Channel proxyChannel = ServerCache.INSTANCE.getProxyChannelWithRequestId(requestId);
                proxyChannel.writeAndFlush(Unpooled.copiedBuffer(msg.getBody().toByteArray()));
                break;
            case TRANSFER_REQUEST:
                requestId = getRequestIdFromHeader(headers);
                proxyChannel = ServerCache.INSTANCE.getProxyChannelWithRequestId(requestId);
                if (proxyChannel != null) {
                    log.info("bind request & proxy channel");
                    proxyChannel.attr(NetworkModel.ChannelAttribute.PROXY_SERVER_CHANNEL).set(ctx.channel());
                    ctx.channel().attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).set(proxyChannel);
                    proxyChannel.config().setOption(ChannelOption.AUTO_READ, true);
                }
                break;
            case TRANSFER_DISCONNECT:
                requestId = getRequestIdFromHeader(headers);
                proxyChannel = ServerCache.INSTANCE.getProxyChannelWithRequestId(requestId);
                proxyChannel.close();
                break;
            default:
                log.warn("operation UNKNOWN");
                break;
        }
    }

    private long getRequestIdFromHeader(Map<String, String> headers) {
        String requestIdStr = headers.get(NetworkModel.ChannelAttributeName.REQUEST_ID);
        return Long.parseLong(requestIdStr);
    }

}
