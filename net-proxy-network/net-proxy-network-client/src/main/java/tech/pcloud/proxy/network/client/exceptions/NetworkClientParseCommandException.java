package tech.pcloud.proxy.network.client.exceptions;

/**
 * @ClassName NetworkClientParseCommandException
 * @Author pandong
 * @Date 2019/1/30 14:45
 **/
public class NetworkClientParseCommandException extends NetworkClientException {
    private static final int code = 1;

    public NetworkClientParseCommandException() {
        super(code);
    }

    public NetworkClientParseCommandException(String message) {
        super(message, code);
    }

    public NetworkClientParseCommandException(String message, Throwable cause) {
        super(message, cause, code);
    }
}
