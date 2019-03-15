package tech.pcloud.proxy.core.util;

import tech.pcloud.proxy.core.ProxyException;
import tech.pcloud.proxy.core.Result;

/**
 * @ClassName ServerUtil
 * @Author pandong
 * @Date 2019/2/19 10:59
 **/
public class ResultGenerate {

    public static <T> Result<T> successResult(T t) {
        return buildResult(0, null, t);
    }

    public static <E extends ProxyException> Result<String> failResult(E e) {
        return buildResult(e.getErrorCode(), e.getMessage(), null);
    }

    public static <E extends ProxyException, T> Result<T> failResult(E e, T t) {
        return buildResult(e.getErrorCode(), e.getMessage(), t);
    }

    public static <T> Result<T> failResult(int code, String message, T t) {
        return buildResult(code, message, t);
    }

    public static <T> Result<T> buildResult(int code, String message, T t) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(t);
        return result;
    }
}
