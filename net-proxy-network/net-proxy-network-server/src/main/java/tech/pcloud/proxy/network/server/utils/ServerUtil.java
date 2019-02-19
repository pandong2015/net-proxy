package tech.pcloud.proxy.network.server.utils;

import tech.pcloud.proxy.core.ProxyException;
import tech.pcloud.proxy.core.Result;

/**
 * @ClassName ServerUtil
 * @Author pandong
 * @Date 2019/2/19 10:59
 **/
public class ServerUtil {

    public static <T> Result<T> successResult(T t) {
        return buildResult(0, t);
    }

    public static <E extends ProxyException> Result<String> failResult(E e) {
        return buildResult(e.getErrorCode(), e.getMessage());
    }

    public static <E extends ProxyException, T> Result<T> failResult(E e, T t) {
        return buildResult(e.getErrorCode(), t);
    }

    public static <T> Result<T> buildResult(int code, T t) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(t);
        return result;
    }
}
