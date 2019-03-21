package tech.pcloud.proxy.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.BootstrapFactory;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.client.utils.ClientProtocolHelper;
import tech.pcloud.proxy.network.client.utils.ConnectionFactory;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.service.CommandServiceFactory;
import tech.pcloud.proxy.network.core.service.Initializer;
import tech.pcloud.proxy.network.core.service.impl.DefaultCommandServiceFactory;

import java.net.InetSocketAddress;
import java.util.Random;

/**
 * @ClassName Client
 * @Author pandong
 * @Date 2019/1/29 17:31
 **/
@Slf4j
public class Client implements Initializer {

    private Bootstrap bootstrap;
    private NioEventLoopGroup pool = new NioEventLoopGroup(1);
    private Channel currentChannel;
    private CommandServiceFactory commandServiceFactory;
    private ClientInfo clientInfo;

    public Client(ClientInfo clientInfo) {
        this(clientInfo, new DefaultCommandServiceFactory(Client.class.getPackage().getName()));
    }

    public Client(ClientInfo clientInfo, CommandServiceFactory commandServiceFactory) {
        this.clientInfo = clientInfo;
        this.commandServiceFactory = commandServiceFactory;
    }

    public CommandServiceFactory getCommandServiceFactory() {
        return commandServiceFactory;
    }

    @Override
    public void init() {
        bootstrap = BootstrapFactory.newInstance(
                BootstrapFactory.BootstrapFactoryConfig.builder()
                        .commandServiceFactory(commandServiceFactory)
                        .pool(pool)
                        .type(BootstrapFactory.BootstrapType.PROXY)
                        .build());
        if (clientInfo.getOpenPort() > 0) {
            bootstrap.localAddress(clientInfo.getOpenPort());
        } else {
            Random random = new Random();
            int port = 0;
            while ((port = random.nextInt(60000)) <= 10000) {

            }
            clientInfo.setOpenPort(port);
        }

        ClientCache.mappingClientInfoWithPort(clientInfo.getOpenPort(), clientInfo);
        Server server = clientInfo.getServer();

        ConnectionFactory.connect(ConnectionFactory.ConnectionFactoryConfig.builder()
                .bootstrap(bootstrap)
                .host(server.getHost())
                .port(server.getPort())
                .transfer(new Transfer() {
                    @Override
                    public void transmit(Channel channel, InetSocketAddress localSocketAddress, InetSocketAddress remoteSocketAddress, ClientInfo clientInfo) {
                        currentChannel = channel;
                        channel.attr(NetworkModel.ChannelAttribute.SERVER).set(server);
                        channel.attr(NetworkModel.ChannelAttribute.PORT).set(localSocketAddress.getPort());
                        log.info("connect server[{}:{}] success, bind port: {}", server.getHost(), server.getPort(), localSocketAddress.getPort());

                        tech.pcloud.proxy.configure.model.Client client = new tech.pcloud.proxy.configure.model.Client();
                        client.setId(clientInfo.getId());
                        client.setPort(localSocketAddress.getPort());
                        log.debug("register client, {}", client);
                        channel.writeAndFlush(ClientProtocolHelper.createRegisterClientRequestProtocol(client))
                                .addListener(new ChannelFutureListener() {

                                    @Override
                                    public void operationComplete(ChannelFuture future) throws Exception {
                                        if (future.isSuccess()) {
                                            log.info("client register request send success.");
                                        }
                                    }
                                });
                    }
                })
                .build());
    }


    public void write(Object msg) {
        currentChannel.writeAndFlush(msg);
    }

    public void disconnect() {
        currentChannel.close();
    }

}
