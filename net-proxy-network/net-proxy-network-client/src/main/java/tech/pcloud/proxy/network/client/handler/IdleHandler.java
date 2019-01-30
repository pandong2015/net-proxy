package tech.pcloud.proxy.network.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

@Slf4j
public class IdleHandler extends IdleStateHandler {
    public static final int DEFAULT_READER_IDLE_TIME_SECONDS = 120;
    public static final int DEFAULT_WRITER_IDLE_TIME_SECONDS = 60;
    public static final int DEFAULT_ALL_IDLE_TIME_SECONDS = 300;

    public IdleHandler() {
        this(DEFAULT_WRITER_IDLE_TIME_SECONDS);
    }

    public IdleHandler(int writerIdleTimeSeconds) {
        this(DEFAULT_READER_IDLE_TIME_SECONDS, writerIdleTimeSeconds);
    }

    public IdleHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds) {
        this(readerIdleTimeSeconds, writerIdleTimeSeconds, DEFAULT_ALL_IDLE_TIME_SECONDS);
    }

    public IdleHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
//    log.trace("state --> " + evt.state().name() + ", first --> " + evt.isFirst());
        switch (evt.state()) {
            case ALL_IDLE:
                log.warn("ALL_IDLE!!!, send new heartbeat");
//                sendHeartbeat(ctx);
                break;
            case WRITER_IDLE:
                log.warn("WRITER_IDLE!!!, send new heartbeat");
//                if (evt.isFirst()) {
                sendHeartbeat(ctx);
//                }
                break;
            case READER_IDLE:
                log.warn("READER_IDLE!!!");
                break;
        }
        super.channelIdle(ctx, evt);
    }

    private void sendHeartbeat(ChannelHandlerContext ctx) {
        ProtocolPackage.Protocol heartbeat = ProtocolHelper.createHeartBeatRequestProtocol();
        ctx.channel().writeAndFlush(heartbeat);
    }
}
