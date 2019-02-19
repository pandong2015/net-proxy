package tech.pcloud.proxy.network.server.exceptions;

/**
 * @ClassName NetworkServerParseCommandException
 * @Author pandong
 * @Date 2019/2/19 10:13
 **/
public class NetworkServerParseCommandException extends NetworkServerException {
    private static final int code = 1;

    public NetworkServerParseCommandException() {
        super(code);
    }

    public NetworkServerParseCommandException(String message) {
        super(message, code);
    }

    public NetworkServerParseCommandException(String message, Throwable cause) {
        super(message, cause, code);
    }
}
