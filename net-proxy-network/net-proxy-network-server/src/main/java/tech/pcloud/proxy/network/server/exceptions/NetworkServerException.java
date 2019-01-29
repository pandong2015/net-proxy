package tech.pcloud.proxy.network.server.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.network.core.exceptions.NetworkException;
import tech.pcloud.proxy.network.server.NetworkServerModel;

/**
 * @ClassName NetworkClientException
 * @Author pandong
 * @Date 2019/1/29 16:00
 **/
public class NetworkServerException extends NetworkException {
    private static final ExceptionLevel level = ExceptionLevel.ERROR;
    private static final Model model = NetworkServerModel.NetworkServerModelFactory.INSTANCE.getModel();

    public NetworkServerException(int code) {
        super(level, model, code);
    }

    public NetworkServerException(String message, int code) {
        super(message, level, model, code);
    }

    public NetworkServerException(String message, Throwable cause, int code) {
        super(message, cause, level, model, code);
    }

    public NetworkServerException(Throwable cause, int code) {
        super(cause, level, model, code);
    }
}
