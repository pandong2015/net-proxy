package tech.pcloud.proxy.core.model;

import com.alibaba.fastjson.JSON;
import tech.pcloud.framework.utility.common.MessageDigestFactory;

import java.util.Base64;

public class BaseObject implements ToJson, ToMd5 {
    @Override
    public String toJson() {
        return JSON.toJSONString(this);
    }

    @Override
    public String toMd5() {
        return Base64.getEncoder().encodeToString(MessageDigestFactory.MD5.digest(toString()));
    }
}
