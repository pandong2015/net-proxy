package tech.pcloud.proxy.configure.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ProxyException;

/**
 * @ClassName ConfigureException
 * @Author pandong
 * @Date 2019/1/28 15:45
 **/
public class ConfigureException extends ProxyException {
    public ConfigureException(ExceptionLevel level, Model model, int code) {
        super(level, model, code);
    }

    public ConfigureException(String message, ExceptionLevel level, Model model, int code) {
        super(message, level, model, code);
    }

    public ConfigureException(String message, Throwable cause, ExceptionLevel level, Model model, int code) {
        super(message, cause, level, model, code);
    }

    public ConfigureException(Throwable cause, ExceptionLevel level, Model model, int code) {
        super(cause, level, model, code);
    }

    public ConfigureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level, Model model, int code) {
        super(message, cause, enableSuppression, writableStackTrace, level, model, code);
    }
}
