package tech.pcloud.proxy.core;

/**
 * @ClassName ProxyException
 * @Author pandong
 * @Date 2019/1/28 10:48
 **/
public class ProxyException extends RuntimeException {
    public enum ExceptionLevel {
        ERROR, WARN, INFO, DEBUD
    }

    private ExceptionLevel level;
    private Model model;
    private int code;

    public ProxyException(ExceptionLevel level, Model model, int code) {
        this.level = level;
        this.model = model;
        this.code = code;
    }

    public ProxyException(String message, ExceptionLevel level, Model model, int code) {
        super(message);
        this.level = level;
        this.model = model;
        this.code = code;
    }

    public ProxyException(String message, Throwable cause, ExceptionLevel level, Model model, int code) {
        super(message, cause);
        this.level = level;
        this.model = model;
        this.code = code;
    }

    public ProxyException(Throwable cause, ExceptionLevel level, Model model, int code) {
        super(cause);
        this.level = level;
        this.model = model;
        this.code = code;
    }

    public ProxyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionLevel level, Model model, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.level = level;
        this.model = model;
        this.code = code;
    }

    public int getErrorCode() {
        return (level.ordinal() + 1) * 1000000 + model.getModelCode() * 1000 + code;
    }

    public ExceptionLevel getLevel() {
        return level;
    }

    public Model getModel() {
        return model;
    }

    public int getCode() {
        return code;
    }
}
