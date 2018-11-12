package tech.pcloud.nnts.client.service;

import io.netty.bootstrap.Bootstrap;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tech.pcloud.framework.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.nnts.client.handler.ClientChannelHandler;
import tech.pcloud.nnts.client.handler.IdleHandler;
import tech.pcloud.nnts.client.handler.ServiceChannelHandler;
import tech.pcloud.nnts.message.TransferProto;

@Slf4j
@Component
public class ConnectionFactory {
    public enum ConnectType {
        PROXY,
        REAL
    }

    @Autowired
    NioEventLoopGroup workerGroup;

    private Bootstrap proxyBootstrap;

    public void connect(ConnectType type, String ip, int port, ChannelFutureListener channelFutureListener) {
        switch (type) {
            case REAL:
                connectRealServer(ip, port, channelFutureListener);
                break;
            case PROXY:
                connectProxyServer(ip, port, channelFutureListener);
                break;
        }
    }

    public void connectProxyServer(String ip, int port, ChannelFutureListener channelFutureListener) {
        if (proxyBootstrap == null) {
            proxyBootstrap = getProxyBootstrap();
        }
        proxyBootstrap.connect(ip, port).addListener(channelFutureListener);
    }

    public void connectRealServer(String ip, int port, ChannelFutureListener channelFutureListener) {
        Bootstrap serviceBootstrap = getServiceBootstrap();
        serviceBootstrap.connect(ip, port).addListener(channelFutureListener);
    }


    public Bootstrap getServiceBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new ServiceChannelHandler());
            }
        });
        return bootstrap;
    }

    public Bootstrap getProxyBootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                channel.pipeline().addLast(new DHSecurityCodecHandler(2048));
                channel.pipeline().addLast(new ProtobufDecoder(TransferProto.Transfer.getDefaultInstance()));
                channel.pipeline().addLast(new ProtobufEncoder());
                channel.pipeline().addLast(new IdleHandler());
                channel.pipeline().addLast(new ClientChannelHandler());
            }
        });
        return bootstrap;
    }
}
