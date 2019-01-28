package tech.pcloud.proxy.store.filesystem.exceptions;

/**
 * @ClassName FileSystemStoreSaveException
 * @Author pandong
 * @Date 2019/1/28 14:33
 **/
public class FileSystemStoreSaveException extends FileSystemStoreException {
    private static ExceptionLevel level = ExceptionLevel.ERROR;
    private static int code = 1;

    public FileSystemStoreSaveException() {
        super(level, code);
    }

    public FileSystemStoreSaveException(String message) {
        super(message, level, code);
    }

    public FileSystemStoreSaveException(String message, Throwable cause) {
        super(message, cause, level, code);
    }
}
