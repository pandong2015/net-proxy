package tech.pcloud.proxy.network.server.handler;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;
import tech.pcloud.proxy.network.server.utils.ServerCache;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @ClassName ProxyServerChannelHandler
 * @Author pandong
 * @Date 2019/2/14 10:35
 **/
@Slf4j
public class ProxyServerChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Channel requestChannel = ctx.channel();
        Channel proxyChannel = requestChannel.attr(NetworkModel.ChannelAttribute.PROXY_SERVER_CHANNEL).get();
        long requestId = requestChannel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).get();
        if (proxyChannel == null) {
            log.debug("request id --> " + requestId + ", no proxy channel.");
            requestChannel.close();
        } else {
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            log.debug("request id --> " + requestId + ", read byte size --> " + bytes.length);
            Map<String, String> headers = Maps.newHashMap();
            headers.put(NetworkModel.ChannelAttributeName.REQUEST_ID, String.valueOf(requestId));
            proxyChannel.writeAndFlush(ProtocolHelper.createTransferProtocol(ProtocolPackage.RequestType.REQUEST, headers, bytes))
                    .addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            log.info("send request data resulr: {}.", channelFuture.isSuccess());
                        }
                    });
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();
        int port = inetSocketAddress.getPort();
        Channel clientChannel = ServerCache.INSTANCE.getClientChannel(port);
        Service service = ServerCache.INSTANCE.getService(port);
        if (clientChannel == null) {
            //没有client端的连接
            ctx.channel().close();
        } else {
            //向client端发起连接请求
            long requestId = IdGenerateService.generate(IdGenerateService.IdType.REQUEST);
            log.debug("create new request, requestId --> " + requestId);
            Channel requestChannel = ctx.channel();
            Client client = ServerCache.INSTANCE.getClientWithPort(port);
            //在client端连接道真实服务前，暂停读取数据
            requestChannel.config().setOption(ChannelOption.AUTO_READ, false);
            //缓存当前channel，在后期传输时使用
            ServerCache.INSTANCE.addProxyChannelMapping(requestId, service, client, requestChannel, clientChannel);
            // 通知client准备传输
            Map<String, String> headers = Maps.newHashMap();
            headers.put(NetworkModel.ChannelAttributeName.REQUEST_ID, String.valueOf(requestId));
            clientChannel.writeAndFlush(ProtocolHelper.createRequestProtocol(Operation.TRANSFER_REQUEST.ordinal(), headers, service.toJson()));
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel requestChannel = ctx.channel();
        Channel proxyChannel = requestChannel.attr(NetworkModel.ChannelAttribute.PROXY_SERVER_CHANNEL).get();
        long requestId = requestChannel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).get();
        Service service = requestChannel.attr(NetworkModel.ChannelAttribute.SERVICE).get();
        if (proxyChannel == null) {
            requestChannel.close();
        } else {
            log.debug("close request, reuqest id --> " + requestId);
            proxyChannel.attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).set(null);
            // @TODO 关闭传输
//            proxyChannel.writeAndFlush(messageService.generateDisconnectRequest(requestId, server, service));
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel requestChannel = ctx.channel();
        Channel proxyChannel = requestChannel.attr(NetworkModel.ChannelAttribute.PROXY_SERVER_CHANNEL).get();
        if (proxyChannel == null) {
            requestChannel.close();
        } else {
            proxyChannel.config().setOption(ChannelOption.AUTO_READ, requestChannel.isWritable());
        }
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
