package tech.pcloud.nnts.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.nnts.core.model.Client;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.model.Service;
import tech.pcloud.nnts.core.service.SpringContext;
import tech.pcloud.nnts.message.TransferProto;
import tech.pcloud.nnts.server.service.MessageService;
import tech.pcloud.nnts.server.service.NNTSService;

import java.net.InetSocketAddress;

@Slf4j
public class ServerChannelHandler extends SimpleChannelInboundHandler<TransferProto.Transfer> {
    private MessageService messageService;
    private Node server;
    private NNTSService nntsService;

    public ServerChannelHandler(){
        log.info("create new ServerChannelHandler");
        messageService = SpringContext.getBean(MessageService.class);
        server = SpringContext.getBean("server", Node.class);
        nntsService = SpringContext.getBean(NNTSService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransferProto.Transfer msg) throws Exception {
        log.info("remoteAddress --> " + msg.getOperation().name());
        switch (msg.getOperation()) {
            case HEARTBEAT:
                ctx.writeAndFlush(messageService.generateHeartbeat(msg, server));
                break;
            case SERVICE_REGISTER:
                registreService(ctx, msg);
                break;
            case CONNECT:
                connect(ctx, msg);
                break;
            case DISCONNECT:
                disconnect(ctx, msg);
                break;
            case TRANSFER:
                transfer(ctx, msg);
                break;
            case CLIENT_REGISTER:
                registre(ctx, msg);
                break;
            default:
                log.warn("operation - UNKNOWN");
                break;
        }
    }

    private void transfer(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        nntsService.transfer(ctx, msg);
    }

    private void disconnect(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        long requestId = msg.getRequestId();
        log.debug("disconnect request, request id --> " + requestId);
        nntsService.disconnect(requestId, ctx.channel());
    }

    private void connect(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        long requestId = msg.getRequestId();
        log.debug("connect request, request id --> " + requestId);
        nntsService.connect(requestId, ctx.channel());
    }

    private void registreService(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        log.info("registre new service --> " + data);
        Service service = JSON.parseObject(data, Service.class);
        Service newService = nntsService.registreService(service, ctx.channel());
        ctx.writeAndFlush(messageService.generateRegistreService(msg, server.getId(), newService));
    }

    private void registre(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        log.info("registre new client --> " + data);
        Client client = JSON.parseObject(data, Client.class);
        nntsService.registreClient(client, ctx.channel());
        ctx.writeAndFlush(messageService.generateClientRegistre(msg, server, server));
    }
}
