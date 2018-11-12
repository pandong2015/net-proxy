package tech.pcloud.nnts.server;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class Startup {


    public static void main(String[] args) {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        new SpringApplicationBuilder()
                .sources(ServerConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }
}
