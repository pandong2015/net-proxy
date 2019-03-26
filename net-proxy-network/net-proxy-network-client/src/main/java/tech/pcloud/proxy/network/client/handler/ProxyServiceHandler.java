package tech.pcloud.proxy.network.client.handler;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.util.Map;

/**
 * tech.pcloud.proxy.network.client.handler
 * created by pando on 2019/3/19 0019 10:39
 */
@Slf4j
public class ProxyServiceHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        Channel serviceChannel = channelHandlerContext.channel();
        Channel proxyChannel = serviceChannel.attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).get();
        Service service = serviceChannel.attr(NetworkModel.ChannelAttribute.SERVICE).get();
        long requestId = serviceChannel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).get();
        if (proxyChannel == null) {
            serviceChannel.close();
        } else {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            log.debug("request id[" + requestId + "], read real server data size --> " + bytes.length);
            Map<String, String> headers = Maps.newConcurrentMap();
            headers.put(NetworkModel.ChannelAttributeName.REQUEST_ID, String.valueOf(requestId));
            proxyChannel.writeAndFlush(ProtocolHelper.createTransferProtocol(ProtocolPackage.RequestType.RESPONSE, headers, bytes))
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            log.info("response transfer send, send result:{}", channelFuture.isSuccess());
                        }
                    });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel realChannel = ctx.channel();
        Channel proxyChannel = realChannel.attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).get();
        Service service = realChannel.attr(NetworkModel.ChannelAttribute.SERVICE).get();
        long requestId = realChannel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).get();
        log.debug("request id[" + requestId + "], send disconnect commend.");
        if (proxyChannel != null) {
            ClientCache.removeServiceChannel(requestId);
            // @TODO 关闭连接
//            proxyChannel.writeAndFlush(messageService.generateDisconnect(requestId, service));
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel realChannel = ctx.channel();
        Channel proxyChannel = realChannel.attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).get();
        if (proxyChannel != null) {
            proxyChannel.config().setOption(ChannelOption.AUTO_READ, realChannel.isWritable());
        }
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
        super.exceptionCaught(ctx, cause);
    }
}
