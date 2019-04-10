package tech.pcloud.proxy.network.client;

import io.netty.channel.Channel;
import tech.pcloud.proxy.network.client.model.ClientInfo;

import java.net.InetSocketAddress;

/**
 * tech.pcloud.proxy.network.client
 * created by pando on 2019/3/21 0021 11:09
 */
public interface Transfer {
    void transmit(Channel channel, InetSocketAddress localSocketAddress, InetSocketAddress remoteSocketAddress, ClientInfo clientInfo);
    void fail();
}
