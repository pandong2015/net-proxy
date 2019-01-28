package tech.pcloud.proxy.configure.xml.core.exceptions;

import tech.pcloud.proxy.core.Model;

/**
 * @ClassName XmlConfigureReadException
 * @Author pandong
 * @Date 2019/1/28 15:46
 **/
public class XmlConfigureReadException extends XmlConfigureException {
    public XmlConfigureReadException(ExceptionLevel level, Model model, int code) {
        super(level, model, code);
    }

    public XmlConfigureReadException(String message, ExceptionLevel level, Model model, int code) {
        super(message, level, model, code);
    }

    public XmlConfigureReadException(String message, Throwable cause, ExceptionLevel level, Model model, int code) {
        super(message, cause, level, model, code);
    }

    public XmlConfigureReadException(Throwable cause, ExceptionLevel level, Model model, int code) {
        super(cause, level, model, code);
    }

    public XmlConfigureReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level, Model model, int code) {
        super(message, cause, enableSuppression, writableStackTrace, level, model, code);
    }
}
