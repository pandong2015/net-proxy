package tech.pcloud.proxy.network.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.framework.utility.common.StringUtils;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

@Slf4j
public class ClientProtocolChannelHandler extends MessageToMessageDecoder<ProtocolPackage.Protocol> {
    public static final AttributeKey<ClientInfo> CLIENT_INFO_ATTRIBUTE_KEY = AttributeKey.newInstance("client_info");

    @Override
    protected void decode(ChannelHandlerContext ctx, ProtocolPackage.Protocol msg, List<Object> out) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();
        ClientInfo clientInfo = ClientCache.getClientInfoWithPort(inetSocketAddress.getPort());
        Operation operation = Operation.getOperation(msg.getOperation().getOperation());
        log.debug("client operation:{}, request type:{} " + operation.name(), msg.getOperation().getType().name());
        Map<String, String> headers = msg.getHeadersMap();
        ProtocolCommand command = ProtocolCommand.newInstance(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.HEADER).set(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.COMMAND).set(command);
        ctx.channel().attr(NetworkModel.ChannelAttribute.OPERATION).set(msg.getOperation());
        ctx.channel().attr(CLIENT_INFO_ATTRIBUTE_KEY).set(clientInfo);
        switch (operation) {
            case HEARTBEAT:
                log.debug("heartbeat success.");
                break;
            case TRANSFER:
                String requestIdStr = headers.get(NetworkModel.ChannelAttributeName.REQUEST_ID);
                if (StringUtils.isNull(requestIdStr)) {
                    ChannelFuture closeFuter = ctx.channel().closeFuture();
                    log.warn("request id is null, close channel[close result:{}].", closeFuter.isSuccess());
                } else {
                    long requestId = Long.parseLong(requestIdStr);
                    Channel serviceChannel = ClientCache.getServiceChannel(requestId);
                    if (serviceChannel == null) {
                        ChannelFuture closeFuter = ctx.channel().closeFuture();
                        log.warn("no service channel, close channel[close result:{}].", closeFuter.isSuccess());
                    } else {
                        ByteBuf buf = ctx.alloc().buffer(msg.getBody().size());
                        buf.writeBytes(msg.getBody().toByteArray());
                        serviceChannel.config().setOption(ChannelOption.AUTO_READ, true);
                        serviceChannel.writeAndFlush(buf).addListener(new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                                log.info("send request to target service result: {}, send bytes size: {}", channelFuture.isSuccess(), msg.getBody().size());
                            }
                        });
                    }
                }
                break;
            case NORMAL:
            case TRANSFER_REQUEST:
                String content = msg.getBody().toStringUtf8();
                out.add(content);
                break;
            case TRANSFER_DISCONNECT:
                Channel serviceChannel = ctx.channel().attr(NetworkModel.ChannelAttribute.PROXY_SERVICE_CHANNEL).get();
                serviceChannel.close();
                break;
            default:
                log.warn("operation UNKNOWN");
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Boolean retry = ctx.channel().attr(NetworkModel.ChannelAttribute.RETRY).get();
        if (retry != null && retry == true) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();
            ClientInfo clientInfo = ClientCache.getClientInfoWithPort(inetSocketAddress.getPort());
            ctx.channel().close();
            clientInfo.getClient().init();
        }
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        if (cause instanceof IOException) {
            ctx.channel().attr(NetworkModel.ChannelAttribute.RETRY).set(true);

        }
        super.exceptionCaught(ctx, cause);
    }
}
