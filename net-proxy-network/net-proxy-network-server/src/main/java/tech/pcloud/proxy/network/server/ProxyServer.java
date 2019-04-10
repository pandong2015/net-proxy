package tech.pcloud.proxy.network.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.core.NetworkModel;
import tech.pcloud.proxy.network.server.handler.ProxyServerChannelHandler;
import tech.pcloud.proxy.network.server.utils.ServerCache;

/**
 * @ClassName ProxyServer
 * @Author pandong
 * @Date 2019/2/13 16:11
 **/
@Slf4j
public class ProxyServer {
    private Service service;
    private EventLoopGroup masterGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap proxy;
    private Server server;

    public ProxyServer(Server server, Service service) {
        this.service = service;
        this.server = server;
        this.masterGroup = new NioEventLoopGroup(server.getMasterPoolSize());
        this.workerGroup = new NioEventLoopGroup(server.getWorkerPoolSize());
    }

    public void init() {
        proxy = new ServerBootstrap();
        proxy.group(masterGroup, workerGroup)
                .attr(NetworkModel.ChannelAttribute.SERVICE, service)
                .attr(NetworkModel.ChannelAttribute.SERVER, server)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ProxyServerChannelHandler());
                    }
                });
        try {
            log.info("proxy server start on port {}", service.getProxyPort());
            proxy.bind(service.getProxyPort()).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        log.info("proxy server start on port {} success.", service.getProxyPort());
                        ServerCache.INSTANCE.changeServiceProxyPortStatus(service, true);
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
        masterGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
