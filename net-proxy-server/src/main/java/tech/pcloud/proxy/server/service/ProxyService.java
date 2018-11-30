package tech.pcloud.proxy.server.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.pcloud.proxy.core.model.Client;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.NodeType;
import tech.pcloud.proxy.core.model.Services;
import tech.pcloud.proxy.core.service.CacheService;
import tech.pcloud.proxy.message.TransferProto;
import tech.pcloud.proxy.server.util.Global;

import java.util.List;

@Slf4j
@Service
public class ProxyService {
    @Autowired
    @Qualifier(value = "server")
    private Node server;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private NodesService nodesService;
    @Autowired
    private MessageService messageService;
    @Autowired
    @Qualifier("bossGroup")
    private EventLoopGroup bossGroup;
    @Autowired
    @Qualifier("workerGroup")
    private EventLoopGroup workerGroup;

    public Client registreClient(Client client, Channel channel) {
        Client newClent = nodesService.insert(client, NodeType.CLIENT);
        cacheService.cacheClientChannel(newClent.getId(), channel);
        channel.attr(Global.ChannelAttribute.CLIENT_ID).set(newClent.getId());
        channel.attr(Global.ChannelAttribute.CLIENT).set(newClent);
        if (client.getServices() != null) {
            client.getServices().forEach(service -> {
                registreService(service, channel);
            });
        }
        return newClent;
    }

    public Services list(long id) {
        List<tech.pcloud.proxy.core.model.Service> serviceList = servicesService.selectNodeService(id);
        Services services = new Services();
        services.setServices(serviceList);
        return services;
    }

    public void stopService(tech.pcloud.proxy.core.model.Service service) {
        servicesService.delete(service);
    }

    public tech.pcloud.proxy.core.model.Service registreService(tech.pcloud.proxy.core.model.Service service, Channel channel) {
        long nodeId = channel.attr(Global.ChannelAttribute.CLIENT_ID).get();
        Node node = nodesService.load(nodeId);
        servicesService.insert(node, service);
        cacheService.cacheServicePortChannel(service.getProxyPort(), channel);
        if (Global.checkProxyServer(service.getProxyPort())) {
            log.info("port is bind, shutdown old service.");
            Global.stopProxyServer(service);
        }
        Global.startProxyServer(node, service);
        return service;
    }

    public void connect(long requestId, Channel channel) {
        Channel requestChannel = cacheService.getRequestChannel(requestId);
        if (requestChannel != null) {
            log.info("bind request & proxy channel");
            requestChannel.attr(Global.ChannelAttribute.PROXY_CHANNEL).set(channel);
            channel.attr(Global.ChannelAttribute.REQUEST_CHANNEL).set(requestChannel);
            requestChannel.config().setOption(ChannelOption.AUTO_READ, true);
        }
    }

    public void disconnect(long requestId, Channel channel) {
        Channel requestChannel = cacheService.getRequestChannel(requestId);
        if (requestChannel != null) {
            cacheService.removeRequestChannel(requestId);
            requestChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void transfer(ChannelHandlerContext ctx, TransferProto.Transfer msg) {
        long requestId = msg.getRequestId();
        log.debug("transfer request, request id --> " + requestId);
        Channel requestChannel = cacheService.getRequestChannel(requestId);
        if (requestChannel != null) {
            ByteBuf buf = ctx.alloc().buffer(msg.getData().size());
            buf.writeBytes(msg.getData().toByteArray());
            requestChannel.writeAndFlush(buf);
        }
    }

    public void beforeConnectProxy(long requestId, Channel requestChannel, Channel proxyChannel, Node client, tech.pcloud.proxy.core.model.Service service) {
        cacheService.cacheRequestChannel(requestId, requestChannel);
        //在client端连接道真实服务前，暂停读取数据
        requestChannel.config().setOption(ChannelOption.AUTO_READ, false);
        requestChannel.attr(Global.ChannelAttribute.REQUEST_ID).set(requestId);
        requestChannel.attr(Global.ChannelAttribute.CLIENT).set(client);
        requestChannel.attr(Global.ChannelAttribute.SERVICE).set(service);

        log.debug("send connect commend...");
        proxyChannel.writeAndFlush(messageService.generateConnectRequest(requestId, server, service));
    }
}
