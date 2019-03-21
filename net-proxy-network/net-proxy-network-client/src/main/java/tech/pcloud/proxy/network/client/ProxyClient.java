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
import tech.pcloud.proxy.network.client.utils.BootstrapFactory;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.service.Initializer;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;

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

    public void connect(long requestId, Channel requestChannel) {
        if (proxyBootstrap == null) {
            throw new BootstrapNotInitException("proxy bootstrap not initializer.");
        }
        proxyBootstrap.connect(service.getHost(), service.getPort()).addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    log.info("connect service success.");
                    Channel proxyServiceChannel = channelFuture.channel();
                    //绑定两个channel，用于后续传输数据
                    proxyServiceChannel.attr(NetworkModel.ChannelAttribute.PROXY_REQUEST_CHANNEL).set(requestChannel);
                    requestChannel.attr(NetworkModel.ChannelAttribute.PROXY_SERVICE_CHANNEL).set(proxyServiceChannel);

                    //回应connect指令，client准备完毕，准备传输
                    Map<String, String> headers = Maps.newHashMap();
                    headers.put(NetworkModel.ChannelAttributeName.REQUEST_ID, String.valueOf(requestId));
                    requestChannel.writeAndFlush(ProtocolHelper.createResponseProtocol(Operation.REQUEST.ordinal(), headers, service.toJson()));

                    //后端服务开始读取数据
                    requestChannel.config().setOption(ChannelOption.AUTO_READ, true);

                    requestChannel.attr(NetworkModel.ChannelAttribute.REQUEST_ID).set(requestId);
                    requestChannel.attr(NetworkModel.ChannelAttribute.SERVICE).set(service);
                } else {
                    log.warn("connect service fail.");
                }
            }
        });
    }
}
