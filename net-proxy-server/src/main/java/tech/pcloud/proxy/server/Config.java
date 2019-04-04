package tech.pcloud.proxy.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.server.service.ServerConfigureService;
import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.service.FileSystemStoreService;

import java.nio.file.Paths;

/**
 * tech.pcloud.proxy.server
 * created by pando on 2019/4/3 0003 15:37
 */
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("tech.pcloud.proxy.server")
public class Config {
    @Value("${server.config.path}")
    private String serverConfigPath;

    @Bean
    public StoreService getStoreService() {
        return new FileSystemStoreService(Paths.get(serverConfigPath));
    }

    @Bean
    public ConfigureService<Server> getConfigureService(@Autowired StoreService storeService) {
        return new ServerConfigureService(storeService);
    }

    @Bean
    public tech.pcloud.proxy.network.server.Server getServer(@Autowired ConfigureService<Server> configureService) {
        tech.pcloud.proxy.network.server.Server server = new tech.pcloud.proxy.network.server.Server(configureService.loadConfigure());
        server.init();
        return server;
    }
}
