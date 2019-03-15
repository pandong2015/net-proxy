package tech.pcloud.proxy.network.server.exceptions;

/**
 * @ClassName NetworkServerParseCommandException
 * @Author pandong
 * @Date 2019/2/19 10:13
 **/
public class NetworkServerNoClientException extends NetworkServerException {
    private static final int code = 2;

    public NetworkServerNoClientException() {
        super(code);
    }

    public NetworkServerNoClientException(String message) {
        super(message, code);
    }

    public NetworkServerNoClientException(String message, Throwable cause) {
        super(message, cause, code);
    }
}
