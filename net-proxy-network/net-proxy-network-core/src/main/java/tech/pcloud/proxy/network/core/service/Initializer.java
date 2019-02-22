package tech.pcloud.proxy.network.core.service;

/**
 * @ClassName Initializer
 * @Author pandong
 * @Date 2019/2/21 14:06
 **/
public interface Initializer {
    default void systemInit() {
        System.setProperty("jdk.crypto.KeyAgreement.legacyKDF", "true");
        init();
    }

    void init();
}
