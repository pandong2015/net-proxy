package tech.pcloud.proxy.store.core.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ProxyException;

/**
 * @ClassName StoreException
 * @Author pandong
 * @Date 2019/1/28 10:42
 **/
public class StoreException extends ProxyException {

    public StoreException(ExceptionLevel level, Model model, int code) {
        super(level, model, code);
    }

    public StoreException(String message, ExceptionLevel level, Model model, int code) {
        super(message, level, model, code);
    }

    public StoreException(String message, Throwable cause, ExceptionLevel level, Model model, int code) {
        super(message, cause, level, model, code);
    }

    public StoreException(Throwable cause, ExceptionLevel level, Model model, int code) {
        super(cause, level, model, code);
    }

    public StoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level, Model model, int code) {
        super(message, cause, enableSuppression, writableStackTrace, level, model, code);
    }
}
