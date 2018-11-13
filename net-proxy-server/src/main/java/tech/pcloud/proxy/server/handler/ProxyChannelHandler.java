package tech.pcloud.proxy.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;
import tech.pcloud.proxy.core.service.CacheService;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.core.service.SpringContext;
import tech.pcloud.proxy.server.service.*;
import tech.pcloud.proxy.server.service.MessageService;
import tech.pcloud.proxy.server.util.Global;
import tech.pcloud.proxy.server.service.NNTSService;

import java.net.InetSocketAddress;

@Slf4j
public class ProxyChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private Node server;
    private Service service;
    private Node client;
    private MessageService messageService;
    private IdGenerateService idGenerateService;
    private CacheService cacheService;
    private NNTSService nntsService;

    public ProxyChannelHandler(Service service, Node client) {
        this.service = service;
        this.client = client;

        server = SpringContext.getBean("server", Node.class);
        messageService = SpringContext.getBean(MessageService.class);
        idGenerateService = SpringContext.getBean(IdGenerateService.class);
        cacheService = SpringContext.getBean(CacheService.class);
        nntsService = SpringContext.getBean(NNTSService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Channel requestChannel = ctx.channel();
        Channel proxyChannel = requestChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).get();
        long requestId = requestChannel.attr(Global.ChannelAttribute.REQUEST_ID).get();
        if (proxyChannel == null) {
            log.debug("request id --> " + requestId + ", no proxy channel.");
            requestChannel.close();
        } else {
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            log.debug("request id --> " + requestId + ", read byte size --> " + bytes.length);
            proxyChannel.writeAndFlush(messageService.generateTransfer(requestId, server, service, bytes));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("new proxy request...");
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().localAddress();
        int port = inetSocketAddress.getPort();
        Channel proxyChannel = cacheService.getServiceChannel(port);
        if (proxyChannel == null) {
            //没有client端的连接
            ctx.channel().close();
        } else {
            //向client端发起连接请求
            long requestId = idGenerateService.generate(IdGenerateService.IdType.REQUEST);
            log.debug("create new request, requestId --> " + requestId);
            //根据requestID，缓存channel，方便在传输数据时使用
            nntsService.beforeConnectProxy(requestId,ctx.channel(), proxyChannel, client, service);
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel requestChannel = ctx.channel();
        Channel proxyChannel = requestChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).get();
        long requestId = requestChannel.attr(Global.ChannelAttribute.REQUEST_ID).get();
        Service service = requestChannel.attr(Global.ChannelAttribute.SERVICE).get();
        if (proxyChannel == null) {
            requestChannel.close();
        } else {
            log.debug("close request, reuqest id --> " + requestId);
            proxyChannel.attr(Global.ChannelAttribute.REQUEST_CHANNEL).set(null);
            proxyChannel.writeAndFlush(messageService.generateDisconnectRequest(requestId, server, service));
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel requestChannel = ctx.channel();
        Channel proxyChannel = requestChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).get();
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
