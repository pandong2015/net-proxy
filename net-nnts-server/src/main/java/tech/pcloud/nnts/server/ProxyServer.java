package tech.pcloud.nnts.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.model.Service;
import tech.pcloud.nnts.core.service.CacheService;
import tech.pcloud.nnts.core.service.SpringContext;
import tech.pcloud.nnts.server.handler.ProxyChannelHandler;

@Slf4j
@Data
public class ProxyServer {
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ServerBootstrap bootstrap = new ServerBootstrap();
    private Service service;
    private Node node;

    public ProxyServer(Service service, Node node) {
        this.service = service;
        this.node = node;
    }

    public int getProxyPort(){
        return service.getProxyPort();
    }

    public void start(){
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProxyChannelHandler(service, node));
                    }
                });
        try {
            bootstrap.bind(service.getProxyPort()).get();
            log.info("start proxy server success!!!");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
