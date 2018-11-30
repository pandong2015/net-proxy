package tech.pcloud.proxy.client.listeners;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import tech.pcloud.proxy.client.service.MessageService;
import tech.pcloud.proxy.client.util.Global;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;

public class ServiceShutdownListener implements ChannelFutureListener {

    private Node client;
    private Node server;
    private Service service;
    private MessageService messageService;

    public ServiceShutdownListener(Node client, Node server, Service service, MessageService messageService) {
        this.client = client;
        this.server = server;
        this.service = service;
        this.messageService = messageService;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        future.channel().attr(Global.ChannelAttribute.SERVER).set(null);
        future.channel().attr(Global.ChannelAttribute.DAEMON).set(false);
        future.channel().writeAndFlush(messageService.generateServiceShutdown(client, service));
    }
}
