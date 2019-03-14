package tech.pcloud.proxy.network.core.service.adaptors;

import com.alibaba.fastjson.JSON;
import tech.pcloud.proxy.network.core.service.GetContentObject;

/**
 * tech.pcloud.proxy.network.core.service
 * created by pando on 2019/3/13 0013 10:23
 */
public interface GetObjectContentObject<T> extends GetContentObject<T> {
    @Override
    default T getContentObject(String content) {
        return JSON.parseObject(content,getContentType());
    }
}
