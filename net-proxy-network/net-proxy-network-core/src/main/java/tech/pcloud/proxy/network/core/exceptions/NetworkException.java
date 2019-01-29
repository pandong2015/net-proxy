package tech.pcloud.proxy.network.core.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ProxyException;

/**
 * @ClassName NetworkException
 * @Author pandong
 * @Date 2019/1/25 14:50
 **/
public class NetworkException extends ProxyException {

    public NetworkException(ExceptionLevel level, Model model, int code) {
        super(level, model, code);
    }

    public NetworkException(String message, ExceptionLevel level, Model model, int code) {
        super(message, level, model, code);
    }

    public NetworkException(String message, Throwable cause, ExceptionLevel level, Model model, int code) {
        super(message, cause, level, model, code);
    }

    public NetworkException(Throwable cause, ExceptionLevel level, Model model, int code) {
        super(cause, level, model, code);
    }

    public NetworkException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level, Model model, int code) {
        super(message, cause, enableSuppression, writableStackTrace, level, model, code);
    }
}
