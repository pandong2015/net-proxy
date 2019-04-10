package tech.pcloud.proxy.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.pcloud.proxy.configure.model.ClientConfig;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.client.service.ClientConfigureService;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.network.client.Client;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.service.FileSystemStoreService;

import java.nio.file.Paths;

/**
 * tech.pcloud.proxy.client
 * created by pando on 2019/4/3 0003 15:51
 */
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("tech.pcloud.proxy.client")
public class Config {
    @Value("${client.config.path}")
    private String clientConfigPath;

    @Bean
    public StoreService getStoreService() {
        return new FileSystemStoreService(Paths.get(clientConfigPath));
    }

    @Bean
    public ConfigureService<ClientConfig> getConfigureService(@Autowired StoreService storeService) {
        return new ClientConfigureService(storeService);
    }

    @Bean
    public ClientConfig getClientConfig(@Autowired ConfigureService<ClientConfig> configureService) {
        ClientConfig clientConfig = configureService.loadConfigure();
        if (clientConfig.getId() == 0) {
            clientConfig.setId(IdGenerateService.generate(IdGenerateService.IdType.CLIENT));
            configureService.saveConfigure(clientConfig);
        }
        return clientConfig;
    }

    @Bean
    public ClientInfo getClientInfo(@Autowired ClientConfig clientConfig) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setId(clientConfig.getId());
        clientInfo.setRetry(clientConfig.isRetry());
        clientInfo.setSleepTime(clientConfig.getSleepTime());
        clientInfo.setOpenPort(clientConfig.getPort());
        clientInfo.setServer(clientConfig.getServer());
        clientInfo.setServices(clientConfig.getServices(clientConfig.getServer()));
        return clientInfo;
    }

    @Bean
    public Client getClient(@Autowired ClientInfo clientInfo) {
        Client client = new Client(clientInfo);
        client.init();
        return client;
    }
}
