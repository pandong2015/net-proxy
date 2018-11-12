package tech.pcloud.nnts.client;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import tech.pcloud.framework.utility.common.HashUtil;
import tech.pcloud.framework.utility.common.NetworkUtils;
import tech.pcloud.nnts.core.model.Node;

@Slf4j
@Configuration
@EnableScheduling
@ComponentScan("tech.pcloud.nnts")
@MapperScan("tech.pcloud.nnts.client.mapper")
@SpringBootApplication
public class ClientConfig {

    @Bean(value = "client")
    public Node getClient() {
        Node client = new Node();
        client.setId(HashUtil.hashByMD5(NetworkUtils.getHardwareAddress()));
        client.setIp(NetworkUtils.getIP(NetworkUtils.IP_VERSION.V4));
        client.setName(NetworkUtils.getHostName());
        log.info("client node, id : "+ client.getId());
        log.info("client node, ip : "+ client.getIp());
        return client;
    }

    @Bean
    public NioEventLoopGroup getWorkerGroup() {
        return new NioEventLoopGroup();
    }


}
