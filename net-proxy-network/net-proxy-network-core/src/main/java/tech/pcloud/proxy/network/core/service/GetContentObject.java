package tech.pcloud.proxy.network.core.service;

import java.lang.reflect.Type;

/**
 * tech.pcloud.proxy.network.core.service
 * created by pando on 2019/3/13 0013 13:26
 */
public interface GetContentObject<T> {
    T getContentObject(String content);

    Type getContentType();
}
