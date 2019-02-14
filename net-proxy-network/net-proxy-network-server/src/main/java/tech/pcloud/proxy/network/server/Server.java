package tech.pcloud.proxy.network.server;

import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.util.Map;

/**
 * @ClassName Server
 * @Author pandong
 * @Date 2019/2/12 17:18
 **/
public class Server {

    private tech.pcloud.proxy.configure.model.Server server;
    private ServerBootstrap bootstrap;
    private EventLoopGroup masterGroup;
    private EventLoopGroup workerGroup;

    public Server(tech.pcloud.proxy.configure.model.Server server) {
        this.server = server;
        init();
    }

    private void init() {
        bootstrap = new ServerBootstrap();
        masterGroup = new NioEventLoopGroup(server.getMasterPoolSize());
        workerGroup = new NioEventLoopGroup(server.getWorkerPoolSize());
        bootstrap.group(masterGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                        channel.pipeline().addLast(new ProtobufDecoder(ProtocolPackage.Protocol.getDefaultInstance()));
                        channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                        channel.pipeline().addLast(new ProtobufEncoder());
//                channel.pipeline().addLast(new MetricsHandler());
//                        channel.pipeline().addLast(new ServerChannelHandler());
                    }
                });
    }
}
