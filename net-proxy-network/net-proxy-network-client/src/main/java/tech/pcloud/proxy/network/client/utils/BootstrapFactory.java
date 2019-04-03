package tech.pcloud.proxy.network.client.utils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.Builder;
import lombok.Data;
import tech.pcloud.framework.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.proxy.network.client.handler.ClientChannelHandler;
import tech.pcloud.proxy.network.client.handler.ClientProtocolChannelHandler;
import tech.pcloud.proxy.network.client.handler.IdleHandler;
import tech.pcloud.proxy.network.core.service.CommandServiceFactory;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

/**
 * tech.pcloud.proxy.network.client.utils
 * created by pando on 2019/3/21 0021 10:05
 */
public class BootstrapFactory {
    public enum BootstrapType {
        PROXY, SERVICE;
    }

    public static Bootstrap newInstance(BootstrapFactoryConfig config) {
        Bootstrap bootstrap = null;
        switch (config.getType()) {
            case PROXY:
                bootstrap = createProxyBootstrap(config.getPool(), config.getCommandServiceFactory());
                break;
            case SERVICE:
                bootstrap = createServiceBootstrap(config.getPool());
                break;
        }
        return bootstrap;
    }

    private static Bootstrap createProxyBootstrap(NioEventLoopGroup pool, CommandServiceFactory commandServiceFactory) {
        Bootstrap bootstrap = new Bootstrap();
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
//                channel.pipeline().addLast(new IdleHandler());
                channel.pipeline().addLast(new ClientProtocolChannelHandler());
                channel.pipeline().addLast(new ClientChannelHandler(commandServiceFactory));
            }
        });
        return bootstrap;
    }

    private static Bootstrap createServiceBootstrap(NioEventLoopGroup pool) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(pool)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast()
                    }
                });
        return bootstrap;
    }

    @Data
    @Builder
    public static class BootstrapFactoryConfig {
        private BootstrapType type;
        private NioEventLoopGroup pool;
        private CommandServiceFactory commandServiceFactory;
    }
}
