package tech.pcloud.proxy.configure.service;

/**
 * @ClassName ConfigureService
 * @Author pandong
 * @Date 2019/1/28 10:31
 **/
public interface ConfigureService<T> {
    void saveConfigure(T config);

    T loadConfigure();
}
