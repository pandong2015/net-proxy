package tech.pcloud.proxy.network.core.service.adaptors;

import com.alibaba.fastjson.JSON;
import tech.pcloud.proxy.core.Result;
import tech.pcloud.proxy.network.core.exceptions.ResponseCommandException;
import tech.pcloud.proxy.network.core.service.GetContentObject;

/**
 * tech.pcloud.proxy.network.core.service.adaptors
 * created by pando on 2019/3/13 0013 11:40
 */
public interface GetResultContentObject<T> extends GetContentObject<T> {
    @Override
    default T getContentObject(String content) {
        Result<T> result = JSON.parseObject(content, getContentType());
        if (result.getCode() != 0) {
            throw new ResponseCommandException("response is error, response code:" + result.getCode());
        }
        return result.getData();
    }
}
