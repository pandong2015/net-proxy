package tech.pcloud.proxy.server;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.server.service.ServerConfigureService;
import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.service.FileSystemStoreService;

import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * @ClassName StertServer
 * @Author pandong
 * @Date 2019/2/21 14:43
 **/
public class Startup {
    public static void main(String[] args){
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        new SpringApplicationBuilder()
                .bannerMode(Banner.Mode.OFF)
                .sources(Config.class)
                .build()
                .run(args);
    }
}
