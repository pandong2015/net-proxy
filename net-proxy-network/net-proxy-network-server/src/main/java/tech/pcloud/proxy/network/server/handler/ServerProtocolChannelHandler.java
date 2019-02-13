package tech.pcloud.proxy.network.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolBody;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ServerProtocolChannelHandler
 * @Author pandong
 * @Date 2019/2/13 11:07
 **/
@Slf4j
public class ServerProtocolChannelHandler extends MessageToMessageCodec<ProtocolPackage.Protocol, ProtocolBody> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolBody msg, List<Object> out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ProtocolPackage.Protocol msg, List<Object> out) throws Exception {
        log.debug("server operation:{}, request type:{} " + msg.getOperation().getOperation(), msg.getOperation().getType().name());
        Operation operation = Operation.getOperation(msg.getOperation().getOperation());
        Map<String, String> headers = msg.getHeadersMap();
        ProtocolCommand command = ProtocolCommand.newInstance(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.HEADER).set(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.COMMAND).set(command);
        ctx.channel().attr(NetworkModel.ChannelAttribute.OPERATION).set(msg.getOperation());
        switch (operation) {
            case HEARTBEAT:
                log.debug("heartbeat request");
                break;
            case NORMAL:
                String content = msg.getBody().toStringUtf8();
                out.add(content);
                break;
            case TRANSFER:
                break;
            case REQUEST:
                break;
            default:
                log.warn("operation UNKNOWN");
                break;
        }
    }
}
