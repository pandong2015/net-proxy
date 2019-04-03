package tech.pcloud.proxy.network.client.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.client.Transfer;
import tech.pcloud.proxy.network.client.model.ClientInfo;

import java.net.InetSocketAddress;

/**
 * tech.pcloud.proxy.network.client.utils
 * created by pando on 2019/3/21 0021 11:02
 */
@Slf4j
public class ConnectionFactory {

    public static void connect(ConnectionFactoryConfig connectionFactoryConfig) {
        connectionFactoryConfig.getBootstrap()
                .connect(connectionFactoryConfig.getHost(), connectionFactoryConfig.getPort())
                .addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()) {
                            log.info("connect {}:{} success.", connectionFactoryConfig.getHost(), connectionFactoryConfig.getPort());
                            if (connectionFactoryConfig.getTransfer() != null) {
                                Channel currentChannel = channelFuture.channel();
                                InetSocketAddress localSocketAddress = (InetSocketAddress) currentChannel.localAddress();
                                InetSocketAddress remoteSocketAddress = (InetSocketAddress) currentChannel.remoteAddress();
                                connectionFactoryConfig.getTransfer()
                                        .transmit(currentChannel, localSocketAddress,
                                                remoteSocketAddress, connectionFactoryConfig.getClientInfo());
                                log.info("transmit data success.");
                            }
                        } else {
                            log.info("connect {}:{} fail.", connectionFactoryConfig.getHost(), connectionFactoryConfig.getPort());
                        }
                    }
                });
    }

    @Data
    @Builder
    public static class ConnectionFactoryConfig {
        private Bootstrap bootstrap;
        private String host;
        private Integer port;
        private Transfer transfer;
        private ClientInfo clientInfo;
    }
}
