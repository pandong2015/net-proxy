package tech.pcloud.proxy.network.client.exceptions;

/**
 * tech.pcloud.proxy.network.client.exceptions
 * created by pando on 2019/3/19 0019 10:28
 */
public class BootstrapNotInitException extends NetworkClientException {
    private static final int code = 2;

    public BootstrapNotInitException() {
        super(code);
    }

    public BootstrapNotInitException(String message) {
        super(message, code);
    }

    public BootstrapNotInitException(String message, Throwable cause) {
        super(message, cause, code);
    }

    public BootstrapNotInitException(Throwable cause) {
        super(cause, code);
    }
}
