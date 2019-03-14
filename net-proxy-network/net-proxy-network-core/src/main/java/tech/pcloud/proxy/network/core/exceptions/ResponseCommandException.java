package tech.pcloud.proxy.network.core.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.network.core.NetworkModel;

/**
 * tech.pcloud.proxy.network.core.exceptions
 * created by pando on 2019/3/13 0013 11:43
 */
public class ResponseCommandException extends NetworkException {
    private static final ExceptionLevel level = ExceptionLevel.ERROR;
    private static final Model model = NetworkModel.NetworkModelFactory.INSTANCE.getModel();
    private static final int code = 1;

    public ResponseCommandException() {
        super(level, model, code);
    }

    public ResponseCommandException(String message) {
            super(message, level, model, code);
    }

    public ResponseCommandException(String message, Throwable cause) {
        super(message, cause, level, model, code);
    }

    public ResponseCommandException(Throwable cause) {
        super(cause, level, model, code);
    }

    public ResponseCommandException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, level, model, code);
    }
}
