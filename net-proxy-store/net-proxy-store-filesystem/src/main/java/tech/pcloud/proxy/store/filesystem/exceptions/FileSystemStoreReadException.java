package tech.pcloud.proxy.store.filesystem.exceptions;

/**
 * @ClassName FileSystemStoreSaveException
 * @Author pandong
 * @Date 2019/1/28 14:33
 **/
public class FileSystemStoreReadException extends FileSystemStoreException {
    private static ExceptionLevel level = ExceptionLevel.ERROR;
    private static int code = 2;

    public FileSystemStoreReadException() {
        super(level, code);
    }

    public FileSystemStoreReadException(String message) {
        super(message, level, code);
    }

    public FileSystemStoreReadException(String message, Throwable cause) {
        super(message, cause, level, code);
    }
}
