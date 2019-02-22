package tech.pcloud.proxy.network.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.ManageProtocolBody;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolBody;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class ClientProtocolChannelHandler extends MessageToMessageCodec<ProtocolPackage.Protocol, ProtocolBody> {


    public ClientProtocolChannelHandler() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolBody msg, List<Object> out) throws Exception {

    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ProtocolPackage.Protocol msg, List<Object> out) throws Exception {
        Operation operation = Operation.getOperation(msg.getOperation().getOperation());
        log.debug("client operation:{}, request type:{} " + operation.name(), msg.getOperation().getType().name());
        Map<String, String> headers = msg.getHeadersMap();
        ProtocolCommand command = ProtocolCommand.newInstance(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.HEADER).set(headers);
        ctx.channel().attr(NetworkModel.ChannelAttribute.COMMAND).set(command);
        ctx.channel().attr(NetworkModel.ChannelAttribute.OPERATION).set(msg.getOperation());
        switch (operation) {
            case HEARTBEAT:
                log.debug("heartbeat success.");
                break;
            case NORMAL:
                String content = msg.getBody().toStringUtf8();
                out.add(content);
                break;
            case TRANSFER:
                // no request
                break;
            case REQUEST:
                // @TODO 发起新连接到server，准备进入传输模式
                break;
            default:
                log.warn("operation UNKNOWN");
                break;
        }
    }

}
