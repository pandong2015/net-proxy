package tech.pcloud.proxy.network.core.exceptions;

/**
 * @ClassName ProtocolException
 * @Author pandong
 * @Date 2019/1/25 14:51
 **/
public class ProtocolException extends NetworkException {
    public ProtocolException(int code) {
        super(code);
    }

    public ProtocolException(String message, int code) {
        super(message, code);
    }

    public ProtocolException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public ProtocolException(Throwable cause, int code) {
        super(cause, code);
    }
}
