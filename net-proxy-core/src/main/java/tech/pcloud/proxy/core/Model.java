package tech.pcloud.proxy.core;

/**
 * @ClassName Model
 * @Author pandong
 * @Date 2019/1/28 10:46
 **/
public interface Model {
    String DEFAULT_ENCODING = "UTF-8";
    int BYTE_BUFFERED_SIZE = 1024 * 8;

    int getModelCode();

    default String getModelName() {
        return getClass().getSimpleName();
    }
}
