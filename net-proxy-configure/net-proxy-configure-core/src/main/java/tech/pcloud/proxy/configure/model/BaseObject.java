package tech.pcloud.proxy.configure.model;

import com.alibaba.fastjson.JSON;
import com.google.common.hash.HashCode;

/**
 * @ClassName BaseObject
 * @Author pandong
 * @Date 2019/1/25 13:39
 **/
public class BaseObject implements ToJson, ToMd5 {
    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toMd5() {
        return HashCode.fromString(toJson()).toString();
    }
}
