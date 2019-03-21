package tech.pcloud.proxy.network.client;

import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.client.exceptions.BootstrapNotInitException;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.BootstrapFactory;
import tech.pcloud.proxy.network.client.utils.ConnectionFactory;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.service.Initializer;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * tech.pcloud.proxy.network.client
 * created by pando on 2019/3/19 0019 10:10
 */
@Slf4j
public class ProxyClient implements Initializer {
    private Bootstrap proxyBootstrap;
    private Service service;
    private NioEventLoopGroup workerGroup;

    public ProxyClient(Service service) {
        this.service = service;
        init();
    }

    @Override
    public void init() {
        workerGroup = new NioEventLoopGroup();
        proxyBootstrap = BootstrapFactory.newInstance(
                BootstrapFactory.BootstrapFactoryConfig.builder()
                        .pool(workerGroup)
                        .type(BootstrapFactory.BootstrapType.SERVICE)
                        .build());
    }

    public void connect(long requestId) {
        if (proxyBootstrap == null) {
            throw new BootstrapNotInitException("proxy bootstrap not initializer.");
        }

        ConnectionFactory.connect(ConnectionFactory.ConnectionFactoryConfig.builder()
                .bootstrap(proxyBootstrap)
                .host(service.getHost())
                .port(service.getPort())
                .transfer(new Transfer() {
                    @Override
                    public void transmit(Channel channel, InetSocketAddress localSocketAddress, ClientInfo clientInfo) {
                        final Channel serviceChannel = channel;
                        log.debug("begin create new connection to proxy server...");
                        ConnectionFactory.connect(ConnectionFactory.ConnectionFactoryConfig.builder()
                                .bootstrap(BootstrapFactory.newInstance(BootstrapFactory.BootstrapFactoryConfig.builder()
                                        .type(BootstrapFactory.BootstrapType.PROXY)
                                        .pool(workerGroup)
                                        .commandServiceFactory(clientInfo.getClient().getCommandServiceFactory())
                                        .build()))
                                .host(clientInfo.getServer().getHost())
                                .port(clientInfo.getServer().getPort())
                                .transfer(new Transfer() {
                                    @Override
                                    public void transmit(Channel channel, InetSocketAddress localSocketAddress, ClientInfo clientInfo) {
                                        //绑定两个channel，用于后续传输数据
                                        channel.attr(NetworkModel.ChannelAttribute.PROXY_SERVICE_CHANNEL).set(serviceChannel);
                                        serviceChannel.attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).set(channel);

                                        //回应connect指令，client准备完毕，准备传输
                                        Map<String, String> headers = Maps.newHashMap();
                                        headers.put(NetworkModel.ChannelAttributeName.REQUEST_ID, String.valueOf(requestId));
                                        channel.writeAndFlush(ProtocolHelper.createResponseProtocol(Operation.REQUEST.ordinal(), headers, service.toJson()));

                                        //后端服务开始读取数据
                                        channel.config().setOption(ChannelOption.AUTO_READ, true);

                                        channel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).set(requestId);
                                        channel.attr(NetworkModel.ChannelAttribute.SERVICE).set(service);
                                    }
                                })
                                .build());
                    }
                })
                .build());

    }
}
