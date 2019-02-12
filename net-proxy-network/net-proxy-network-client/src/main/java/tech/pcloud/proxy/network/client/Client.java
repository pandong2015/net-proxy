package tech.pcloud.proxy.network.client;

import com.google.common.collect.Maps;
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
import tech.pcloud.proxy.network.client.handler.ProtocolChannelHandler;
import tech.pcloud.proxy.network.client.handler.IdleHandler;
import tech.pcloud.proxy.network.core.service.CommandServiceFactory;
import tech.pcloud.proxy.network.core.service.impl.DefaultCommandServiceFactory;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.util.Map;

/**
 * @ClassName Client
 * @Author pandong
 * @Date 2019/1/29 17:31
 **/
@Slf4j
public class Client {
    private static final Map<Server, Client> clientCache = Maps.newConcurrentMap();
    private Bootstrap bootstrap;
    private NioEventLoopGroup pool = new NioEventLoopGroup(1);
    private Server server;
    private Channel currentChannel;
    private CommandServiceFactory commandServiceFactory;

    public Client(Server server) {
        this(server, new DefaultCommandServiceFactory(Client.class.getPackage().getName()));
    }

    public Client(Server server, CommandServiceFactory commandServiceFactory) {
        this.server = server;
        this.commandServiceFactory = commandServiceFactory;
        init();
        connectServer();
    }

    private void init() {
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
                channel.pipeline().addLast(new ProtocolChannelHandler());
                channel.pipeline().addLast(new ClientChannelHandler(commandServiceFactory));
            }
        });
    }

    public void connectServer() {
        bootstrap.connect(server.getHost(), server.getPort()).addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    log.info("connect server[{}:{}] success.", server.getHost(), server.getPort());
                    currentChannel = future.channel();
                    clientCache.put(server, getClient());
                } else {
                    log.info("connect server[{}:{}] fail.", server.getHost(), server.getPort());
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

    public static Client getInstance(Server server) {
        if (!clientCache.containsKey(server)) {
            return new Client(server);
        }
        return clientCache.get(server);
    }
}
