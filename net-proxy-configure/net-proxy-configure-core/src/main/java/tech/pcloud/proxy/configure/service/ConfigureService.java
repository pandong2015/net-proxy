package tech.pcloud.proxy.configure.service;

import tech.pcloud.proxy.configure.model.Node;

/**
 * @ClassName ConfigureService
 * @Author pandong
 * @Date 2019/1/28 10:31
 **/
public interface ConfigureService<T extends Node> {
    void saveConfigure(T config);

    T loadConfigure();
}
