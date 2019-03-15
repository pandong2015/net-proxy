package tech.pcloud.proxy.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.framework.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.network.client.handler.ClientChannelHandler;
import tech.pcloud.proxy.network.client.handler.ClientProtocolChannelHandler;
import tech.pcloud.proxy.network.client.handler.IdleHandler;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.network.client.utils.ClientCache;
import tech.pcloud.proxy.network.client.utils.ClientProtocolHelper;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.core.service.CommandServiceFactory;
import tech.pcloud.proxy.network.core.service.Initializer;
import tech.pcloud.proxy.network.core.service.impl.DefaultCommandServiceFactory;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.net.InetSocketAddress;

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

    @Override
    public void init() {
        bootstrap = new Bootstrap();
        bootstrap.group(pool);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                channel.pipeline().addLast(new DHSecurityCodecHandler(2048));
                channel.pipeline().addLast(new ProtobufDecoder(ProtocolPackage.Protocol.getDefaultInstance()));
                channel.pipeline().addLast(new ProtobufEncoder());
                channel.pipeline().addLast(new IdleHandler());
                channel.pipeline().addLast(new ClientProtocolChannelHandler());
                channel.pipeline().addLast(new ClientChannelHandler(commandServiceFactory));
            }
        });
        connectServer();
    }

    public void connectServer() {
        if (clientInfo.getOpenPort() > 0) {
            bootstrap.localAddress(clientInfo.getOpenPort());
        }
        Server server = clientInfo.getServer();
        bootstrap.connect(server.getHost(), server.getPort()).addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    currentChannel = future.channel();
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) currentChannel.localAddress();
                    currentChannel.attr(NetworkModel.ChannelAttribute.SERVER).set(server);
                    currentChannel.attr(NetworkModel.ChannelAttribute.PORT).set(inetSocketAddress.getPort());
                    ClientCache.mappingClientInfoWithPort(inetSocketAddress.getPort(), clientInfo);
                    log.info("connect server[{}:{}] success, bind port: {}", server.getHost(), server.getPort(), inetSocketAddress.getPort());
                    register();
                } else {
                    log.info("connect server[{}:{}] fail.", server.getHost(), server.getPort());
                }
            }
        });
    }

    private void register() {
        int port = currentChannel.attr(NetworkModel.ChannelAttribute.PORT).get();
        tech.pcloud.proxy.configure.model.Client client = new tech.pcloud.proxy.configure.model.Client();
        client.setId(clientInfo.getId());
        client.setPort(port);
        log.debug("register client, {}", client);
        ChannelFuture future = currentChannel.writeAndFlush(ClientProtocolHelper.createRegisterClientRequestProtocol(client));
        future.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("client register request send success.");
                }
            }
        });
    }

    public void write(Object msg) {
        currentChannel.writeAndFlush(msg);
    }

    public void disconnect() {
        currentChannel.close();
    }

    private Client getClient() {
        return this;
    }

}
