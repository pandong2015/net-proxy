package tech.pcloud.proxy.configure.xml.core.exceptions;

import tech.pcloud.proxy.configure.exceptions.ConfigureException;
import tech.pcloud.proxy.core.Model;

/**
 * @ClassName XmlConfigureException
 * @Author pandong
 * @Date 2019/1/28 15:45
 **/
public class XmlConfigureException extends ConfigureException {
    public XmlConfigureException(ExceptionLevel level, Model model, int code) {
        super(level, model, code);
    }

    public XmlConfigureException(String message, ExceptionLevel level, Model model, int code) {
        super(message, level, model, code);
    }

    public XmlConfigureException(String message, Throwable cause, ExceptionLevel level, Model model, int code) {
        super(message, cause, level, model, code);
    }

    public XmlConfigureException(Throwable cause, ExceptionLevel level, Model model, int code) {
        super(cause, level, model, code);
    }

    public XmlConfigureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level, Model model, int code) {
        super(message, cause, enableSuppression, writableStackTrace, level, model, code);
    }
}
