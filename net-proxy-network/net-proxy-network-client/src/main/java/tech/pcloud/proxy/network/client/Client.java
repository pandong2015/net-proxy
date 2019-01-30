package tech.pcloud.proxy.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import tech.pcloud.framework.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.proxy.network.client.handler.ClientChannelHandler;
import tech.pcloud.proxy.network.client.handler.ProtocolChannelHandler;
import tech.pcloud.proxy.network.client.handler.IdleHandler;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

/**
 * @ClassName Client
 * @Author pandong
 * @Date 2019/1/29 17:31
 **/
public class Client {
    private Bootstrap bootstrap;
    private NioEventLoopGroup pool = new NioEventLoopGroup(1);
    private int proxyThreadNum;

    public Client(int proxyThreadNum) {
        this.proxyThreadNum = proxyThreadNum;
        init();
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
                channel.pipeline().addLast(new ClientChannelHandler(null));
            }
        });
    }
}
