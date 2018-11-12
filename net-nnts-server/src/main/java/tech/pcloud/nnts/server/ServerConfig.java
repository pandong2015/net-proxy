package tech.pcloud.nnts.server;

import io.netty.bootstrap.ServerBootstrap;
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
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import tech.pcloud.framework.netty.handler.DHSecurityCodecHandler;
import tech.pcloud.framework.utility.common.HashUtil;
import tech.pcloud.framework.utility.common.NetworkUtils;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.model.NodeType;
import tech.pcloud.nnts.core.model.Service;
import tech.pcloud.nnts.core.service.CacheService;
import tech.pcloud.nnts.message.TransferProto;
import tech.pcloud.nnts.server.handler.ServerChannelHandler;
import tech.pcloud.nnts.server.service.NodesService;
import tech.pcloud.nnts.server.service.ServicesService;
import tech.pcloud.nnts.server.util.Global;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
@Configuration
@SpringBootApplication
@ComponentScan("tech.pcloud.nnts")
@MapperScan("tech.pcloud.nnts.server.mapper")
@EnableConfigurationProperties({ServerConfig.ServerProperties.class})
public class ServerConfig {

    @Data
    @ConfigurationProperties(prefix = "nat.nnts.server")
    public static class ServerProperties {
        private String name;
        private String ip;
        private int port;
        private int backlog;
    }

    @Bean(value = "server")
    public Node getServer(@Autowired ServerProperties properties) {
        Node node = new Node();
        node.setId(HashUtil.hashByMD5(NetworkUtils.getHardwareAddress()));
        node.setIp(NetworkUtils.getIP(NetworkUtils.IP_VERSION.V4));
        node.setName(properties.getName());
        node.setPort(properties.getPort());
        node.setType(NodeType.SERVER.getType());
        log.info("server id : " + node.getId());
        log.info("server ip : " + node.getIp());
        return node;
    }


    @Bean(value = "bossGroup")
    public EventLoopGroup getBossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean(value = "workerGroup")
    public EventLoopGroup getWorkerGroup() {
        return new NioEventLoopGroup();
    }

    @Data
    @Component
    public static class Server {
        @Autowired
        @Qualifier("bossGroup")
        private EventLoopGroup bossGroup;
        @Autowired
        @Qualifier("workerGroup")
        private EventLoopGroup workerGroup;
        @Autowired
        ServerProperties properties;
        @Autowired
        NodesService nodesService;
        @Autowired
        ServicesService servicesService;
        private ServerBootstrap bootstrap;
        @Autowired
        private CacheService cacheService;

        @PostConstruct
        public void init() {
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, properties.getBacklog())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            channel.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            channel.pipeline().addLast(new DHSecurityCodecHandler(2048));
                            channel.pipeline().addLast(new ProtobufDecoder(TransferProto.Transfer.getDefaultInstance()));
                            channel.pipeline().addLast(new ProtobufEncoder());
//                            channel.pipeline().addLast(new IdleHandler());
                            channel.pipeline().addLast(new ServerChannelHandler());
                        }
                    });
            try {
                log.info("proxy server start on port " + properties.getPort());
                bootstrap.bind(properties.getPort()).get();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            startProxyServer();
        }

        private void startProxyServer() {
            List<Node> nodes = nodesService.selectAll();
            nodes.stream()
                    .filter(node -> {
                        if(cacheService.getClientChannel(node.getId()) == null){
                            log.warn("client not connection, don't start proxy server.");
                            return false;
                        }
                        return true;
                    })
                    .forEach(node -> {
                        List<Service> services = servicesService.selectNodeService(node.getId());
                        services.forEach(service -> Global.startProxyServer(node, service));
                    });
        }

        @PreDestroy
        public void destroy() {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
