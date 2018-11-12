package tech.pcloud.nnts.client.handler;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.nnts.client.service.ConnectionFactory;
import tech.pcloud.nnts.client.service.MessageService;
import tech.pcloud.nnts.client.service.ServerService;
import tech.pcloud.nnts.client.service.ServicesService;
import tech.pcloud.nnts.client.util.Global;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.model.Service;
import tech.pcloud.nnts.core.service.CacheService;
import tech.pcloud.nnts.core.service.SpringContext;
import tech.pcloud.nnts.message.TransferProto;

import java.net.InetSocketAddress;
import java.util.List;

@Slf4j
public class ClientChannelHandler extends SimpleChannelInboundHandler<TransferProto.Transfer> {

    private CacheService cacheService;
    private ConnectionFactory connectionFactory;
    private MessageService messageService;
    private ServicesService servicesService;
    private ServerService serverService;

    public ClientChannelHandler() {
        cacheService = SpringContext.getBean(CacheService.class);
        connectionFactory = SpringContext.getBean(ConnectionFactory.class);
        messageService = SpringContext.getBean(MessageService.class);
        servicesService = SpringContext.getBean(ServicesService.class);
        serverService = SpringContext.getBean(ServerService.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TransferProto.Transfer msg) throws Exception {
        log.debug("operation --> " + msg.getOperation().name());
        switch (msg.getOperation()) {
            case HEARTBEAT:
                break;
            case SERVICE_REGISTER:
                registerService(ctx, msg);
                break;
            case CONNECT:
                connect(ctx, msg);
                break;
            case TRANSFER:
                transfer(ctx, msg);
                break;
            case CLIENT_REGISTER:
                register(ctx, msg);
                break;
            case DISCONNECT:
                disconnect(ctx, msg);
                break;
            default:
                log.warn("operation UNKNOWN");
                break;
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("channelInactive ...");
        Boolean daemon = ctx.channel().attr(Global.ChannelAttribute.DAEMON).get();
        if (daemon != null && daemon) {
            Node server = ctx.channel().attr(Global.ChannelAttribute.SERVER).get();
            Global.setConnectStatus(server, Global.ClientConnectStatus.SHUTDOWN);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage(), cause);
    }

    private void disconnect(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        log.debug("close connect, request id --> " + msg.getRequestId());
        Channel proxyChannel = ctx.channel();
        Channel realChannel = proxyChannel.attr(Global.ChannelAttribute.REQUEST_CHANNEL).get();
        if (realChannel != null) {
            proxyChannel.attr(Global.ChannelAttribute.REQUEST_CHANNEL).set(null);
            proxyChannel.close();
            realChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void transfer(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        log.debug("begin transfer data, request id --> " + msg.getRequestId());
        Channel realChannel = cacheService.getRequestServiceChannel(msg.getRequestId());
        if (realChannel != null) {
            ByteBuf buf = ctx.alloc().buffer(msg.getData().size());
            buf.writeBytes(msg.getData().toByteArray());
            realChannel.writeAndFlush(buf);
        }
    }

    private void connect(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        long requestId = msg.getRequestId();
        String data = msg.getData().toStringUtf8();
        Service service = JSON.parseObject(data, Service.class);
        Node server = ctx.channel().attr(Global.ChannelAttribute.SERVER).get();
        log.debug("request id --> " + requestId);
        log.debug("begin connect real server...");
        //连接后端服务
        connectionFactory.connect(ConnectionFactory.ConnectType.REAL, service.getLocalIp(),
                service.getLocalPort(), new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (future.isSuccess()) {
                            log.debug("connect real server success.");
                            Channel realChannel = future.channel();

                            //后端服务连接成功，发起新的连接至代理服务器，准备传输数据
                            log.debug("begin conect proxy server...");
                            connectionFactory.connect(ConnectionFactory.ConnectType.PROXY
                                    , server.getIp(), server.getPort(), new ChannelFutureListener() {
                                        @Override
                                        public void operationComplete(ChannelFuture future) throws Exception {
                                            if (future.isSuccess()) {
                                                log.debug("conect proxy server success.");
                                                Channel proxyChannel = future.channel();
                                                //绑定两个channel，用于后续传输数据
                                                proxyChannel.attr(Global.ChannelAttribute.REQUEST_CHANNEL).set(realChannel);
                                                realChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).set(proxyChannel);

                                                //回应connect指令，client准备完毕，准备传输
                                                proxyChannel.writeAndFlush(messageService.generateConnect(requestId, service));

                                                //后端服务开始读取数据
                                                realChannel.config().setOption(ChannelOption.AUTO_READ, true);

                                                //缓存requestId和后端服务连接的关系
                                                cacheService.cacheServiceRequestChannel(requestId, realChannel);
                                                realChannel.attr(Global.ChannelAttribute.REQUEST_ID).set(requestId);
                                                realChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).set(proxyChannel);
                                                realChannel.attr(Global.ChannelAttribute.SERVICE).set(service);
                                                realChannel.attr(Global.ChannelAttribute.SERVER).set(server);
                                            } else {
                                                log.debug("conect proxy server fail.");
                                                ctx.writeAndFlush(messageService.generateDisconnect(requestId, service));
                                            }
                                        }
                                    });
                        } else {
                            //连接失败，发送断开连接指令
                            log.warn("Service[" + data + "] request fail.");
                            ctx.writeAndFlush(messageService.generateDisconnect(requestId, service));
                        }
                    }
                });
    }

    private void registerService(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        String data = msg.getData().toStringUtf8();
        Service service = JSON.parseObject(data, Service.class);
        log.info("service [" + service.getName() + "] register success");
    }

    private void register(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String data = msg.getData().toStringUtf8();
        Node server = JSON.parseObject(data, Node.class);
        server.setIp(inetSocketAddress.getAddress().getHostAddress());
        server.setPort(inetSocketAddress.getPort());
        log.info("client register success, " + data);
        //注册数据库中的所有service
        ctx.channel().attr(Global.ChannelAttribute.SERVER).set(server);
        Global.setConnectStatus(server, Global.ClientConnectStatus.CONNECT);
//        serverService.updateServerByName(server);
        List<Service> services = servicesService.selectAll();
        services.forEach(service -> {
            ctx.writeAndFlush(messageService.generateRegistreService(service));
        });

    }
}
