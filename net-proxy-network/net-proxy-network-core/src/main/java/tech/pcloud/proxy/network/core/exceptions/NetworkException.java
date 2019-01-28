package tech.pcloud.proxy.network.core.exceptions;

/**
 * @ClassName NetworkException
 * @Author pandong
 * @Date 2019/1/25 14:50
 **/
public class NetworkException extends RuntimeException {
    int code;

    public NetworkException(int code) {
        this.code = code;
    }

    public NetworkException(String message, int code) {
        super(message);
        this.code = code;
    }

    public NetworkException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public NetworkException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
