package tech.pcloud.nnts.client.listeners;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import tech.pcloud.nnts.client.service.MessageService;
import tech.pcloud.nnts.client.util.Global;
import tech.pcloud.nnts.core.model.Node;

import java.net.InetSocketAddress;

public class ServiceRegisterListener implements ChannelFutureListener {

    private Node client;
    private Node server;
    private MessageService messageService;

    public ServiceRegisterListener(Node client, Node server, MessageService messageService) {
        this.client = client;
        this.server = server;
        this.messageService = messageService;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) future.channel().localAddress();
        client.setIp(inetSocketAddress.getAddress().getHostAddress());
        client.setPort(inetSocketAddress.getPort());
        future.channel().attr(Global.ChannelAttribute.SERVER).set(server);
        future.channel().attr(Global.ChannelAttribute.DAEMON).set(true);
        future.channel().writeAndFlush(messageService.generateRegistre(client));
    }
}
