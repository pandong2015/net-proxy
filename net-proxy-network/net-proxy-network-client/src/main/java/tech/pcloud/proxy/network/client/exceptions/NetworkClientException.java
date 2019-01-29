package tech.pcloud.proxy.network.client.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.network.client.NetworkClientModel;
import tech.pcloud.proxy.network.core.exceptions.NetworkException;

/**
 * @ClassName NetworkClientException
 * @Author pandong
 * @Date 2019/1/29 16:00
 **/
public class NetworkClientException extends NetworkException {
    private static final ExceptionLevel level = ExceptionLevel.ERROR;
    private static final Model model = NetworkClientModel.NetworkClientModelFactory.INSTANCE.getModel();

    public NetworkClientException(int code) {
        super(level, model, code);
    }

    public NetworkClientException(String message, int code) {
        super(message, level, model, code);
    }

    public NetworkClientException(String message, Throwable cause, int code) {
        super(message, cause, level, model, code);
    }

    public NetworkClientException(Throwable cause, int code) {
        super(cause, level, model, code);
    }
}
