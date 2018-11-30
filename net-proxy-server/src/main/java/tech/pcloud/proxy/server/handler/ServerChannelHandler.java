package tech.pcloud.proxy.server.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.core.model.Client;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;
import tech.pcloud.proxy.core.model.Services;
import tech.pcloud.proxy.core.service.SpringContext;
import tech.pcloud.proxy.message.TransferProto;
import tech.pcloud.proxy.server.service.MessageService;
import tech.pcloud.proxy.server.service.ProxyService;
import tech.pcloud.proxy.server.util.Global;

@Slf4j
public class ServerChannelHandler extends SimpleChannelInboundHandler<TransferProto.Transfer> {
    private MessageService messageService;
    private Node server;
    private ProxyService proxyService;

    public ServerChannelHandler() {
        log.info("create new ServerChannelHandler");
        messageService = SpringContext.getBean(MessageService.class);
        server = SpringContext.getBean("server", Node.class);
        proxyService = SpringContext.getBean(ProxyService.class);
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
            case SERVICE_SHUTDOWN:
                shutdownService(ctx, msg);
                break;
            case SERVICE_LIST:
                listService(ctx, msg);
                break;
            default:
                log.warn("operation - UNKNOWN");
                break;
        }
    }

    private void transfer(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        proxyService.transfer(ctx, msg);
    }

    private void disconnect(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        long requestId = msg.getRequestId();
        log.debug("disconnect request, request id --> " + requestId);
        proxyService.disconnect(requestId, ctx.channel());
    }

    private void connect(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        long requestId = msg.getRequestId();
        log.debug("connect request, request id --> " + requestId);
        proxyService.connect(requestId, ctx.channel());
    }

    private void registreService(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        log.info("registre new service --> " + data);
        Service service = JSON.parseObject(data, Service.class);
        Service newService = proxyService.registreService(service, ctx.channel());
        ctx.writeAndFlush(messageService.generateRegistreService(msg, server.getId(), newService));
    }

    private void registre(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        log.info("registre new client --> " + data);
        Client client = JSON.parseObject(data, Client.class);
        proxyService.registreClient(client, ctx.channel());
        ctx.writeAndFlush(messageService.generateClientRegistre(msg, server, server));
    }

    private void shutdownService(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        log.info("shutdown service --> " + data);
        Service service = JSON.parseObject(data, Service.class);
        Global.stopProxyServer(service);
        proxyService.stopService(service);
        ctx.writeAndFlush(messageService.generateShutdownService(msg, server.getId(), service));
    }

    private void listService(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        log.info("list services, request client: "+ data);
        Client client = JSON.parseObject(data, Client.class);
        Services services = proxyService.list(client.getId());
        ctx.writeAndFlush(messageService.generateListService(msg, server.getId(), services));
    }
}
