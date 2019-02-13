package tech.pcloud.proxy.network.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.core.protocol.ProtocolBody;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.util.List;

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

    }
}
