package tech.pcloud.proxy.client;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import tech.pcloud.proxy.configure.model.ClientConfig;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.client.service.ClientConfigureService;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.network.client.Client;
import tech.pcloud.proxy.network.client.model.ClientInfo;
import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.service.FileSystemStoreService;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @ClassName StartClient
 * @Author pandong
 * @Date 2019/2/21 15:12
 **/
public class Startup {
    public static void main(String[] args) {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(Config.class)
                .build()
                .run(args);
    }
}
