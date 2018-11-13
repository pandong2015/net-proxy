package tech.pcloud.proxy.client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.client.service.MessageService;
import tech.pcloud.proxy.client.util.Global;
import tech.pcloud.proxy.core.model.Service;
import tech.pcloud.proxy.core.service.CacheService;
import tech.pcloud.proxy.core.service.SpringContext;

@Slf4j
public class ServiceChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private CacheService cacheService;
    private MessageService messageService;

    public ServiceChannelHandler() {
        cacheService = SpringContext.getBean(CacheService.class);
        messageService = SpringContext.getBean(MessageService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        Channel realChannel = ctx.channel();
        Channel proxyChannel = realChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).get();
        Service service = realChannel.attr(Global.ChannelAttribute.SERVICE).get();
        long requestId = realChannel.attr(Global.ChannelAttribute.REQUEST_ID).get();
        if (proxyChannel == null) {
            realChannel.close();
        } else {
            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            log.debug("request id[" + requestId + "], read real server data size --> " + bytes.length);
            proxyChannel.writeAndFlush(messageService.generateTransfer(requestId, service, bytes));
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel realChannel = ctx.channel();
        Channel proxyChannel = realChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).get();
        Service service = realChannel.attr(Global.ChannelAttribute.SERVICE).get();
        long requestId = realChannel.attr(Global.ChannelAttribute.REQUEST_ID).get();
        log.debug("request id[" + requestId + "], send disconnect commend.");
        if (proxyChannel != null) {
            cacheService.removeServiceRequestChannel(requestId);
            proxyChannel.writeAndFlush(messageService.generateDisconnect(requestId, service));
        }
        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Channel realChannel = ctx.channel();
        Channel proxyChannel = realChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).get();
        if (proxyChannel != null) {
            proxyChannel.config().setOption(ChannelOption.AUTO_READ, realChannel.isWritable());
        }
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
        super.exceptionCaught(ctx, cause);
    }
}
