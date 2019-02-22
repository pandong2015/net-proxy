package tech.pcloud.proxy.client;

import tech.pcloud.proxy.configure.model.ClientConfig;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.client.service.ClientConfigureService;
import tech.pcloud.proxy.network.client.Client;
import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.service.FileSystemStoreService;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @ClassName StartClient
 * @Author pandong
 * @Date 2019/2/21 15:12
 **/
public class StartClient {
    public static void main(String[] args){
        StoreService storeService = null;
        try {
            storeService = new FileSystemStoreService(Paths.get(StartClient.class.getResource("/Client.xml").toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ConfigureService<ClientConfig> configureService = new ClientConfigureService(storeService);
        ClientConfig clientConfig = configureService.loadConfigure();
        clientConfig.getServers().forEach(s -> {
            Client client = new Client(s);
            client.init();
        });

    }
}
