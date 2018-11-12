package tech.pcloud.nnts.client;

import org.springframework.boot.Banner;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class Startup {


    public static void main(String[] args) {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        new SpringApplicationBuilder()
                .sources(ClientConfig.class)
                .bannerMode(Banner.Mode.OFF)
                .build()
                .run(args);
    }
}
