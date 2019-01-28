package tech.pcloud.proxy.store.core.service;

/**
 * @ClassName StoreService
 * @Author pandong
 * @Date 2019/1/28 15:16
 **/
public interface StoreService<T> {
    void save(T t);

    T load();
}
