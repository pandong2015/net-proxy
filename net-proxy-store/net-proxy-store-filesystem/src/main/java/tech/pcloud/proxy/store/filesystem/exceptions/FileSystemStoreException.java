package tech.pcloud.proxy.store.filesystem.exceptions;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.store.core.exceptions.StoreException;
import tech.pcloud.proxy.store.filesystem.FileSystemStoreModel;

/**
 * @ClassName FileSystemStoreException
 * @Author pandong
 * @Date 2019/1/28 11:01
 **/
public class FileSystemStoreException extends StoreException {
    private final static Model model = FileSystemStoreModel.FileSystemStoreModelFactory.INSTANCE.getModel();

    public FileSystemStoreException(ExceptionLevel level, int code) {
        super(level, model, code);
    }

    public FileSystemStoreException(String message, ExceptionLevel level, int code) {
        super(message, level, model, code);
    }

    public FileSystemStoreException(String message, Throwable cause, ExceptionLevel level, int code) {
        super(message, cause, level, model, code);
    }
}
