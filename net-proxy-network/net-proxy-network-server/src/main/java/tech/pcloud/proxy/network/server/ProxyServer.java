package tech.pcloud.proxy.network.server;

import com.google.common.collect.Lists;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Service;

import java.util.List;

/**
 * @ClassName ProxyServer
 * @Author pandong
 * @Date 2019/2/13 16:11
 **/
public class ProxyServer {
    private Service service;
    private List<Client> clients = Lists.newArrayList();
    private EventLoopGroup masterGroup;
    private EventLoopGroup workerGroup;
    private ServerBootstrap proxy;

    public ProxyServer(Server server, Service service, Client client){
        this.service = service;
        this.clients.add(client);
        this.masterGroup = new NioEventLoopGroup(server.getMasterPoolSize());
        this.workerGroup = new NioEventLoopGroup(server.getWorkerPoolSize());
    }

    public void addClient(Client client){
        clients.add(client);
    }

    private void init(){
        proxy = new ServerBootstrap();
        proxy.group(masterGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new ProxyChannelHandler());
                    }
                });
    }

    public void shutdown(){
        masterGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
