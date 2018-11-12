package tech.pcloud.nnts.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.nnts.core.service.SpringContext;
import tech.pcloud.nnts.server.service.MessageService;

@Slf4j
public class IdleHandler extends IdleStateHandler {
    public static final int DEFAULT_READER_IDLE_TIME_SECONDS = 120;
    public static final int DEFAULT_WRITER_IDLE_TIME_SECONDS = 60;
    public static final int DEFAULT_ALL_IDLE_TIME_SECONDS = 300;

    private MessageService messageService;

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
        messageService = SpringContext.getBean(MessageService.class);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
//    log.trace("state --> " + evt.state().name() + ", first --> " + evt.isFirst());
        switch (evt.state()) {
            case ALL_IDLE:
                break;
            case WRITER_IDLE:
                break;
            case READER_IDLE:
                log.info("read IDLE");
                break;
        }
        super.channelIdle(ctx, evt);
    }

    private void readHeartbeat(ChannelHandlerContext ctx) {
//        ctx.channel().writeAndFlush(messageService.generateHeartbeat());
    }
}
