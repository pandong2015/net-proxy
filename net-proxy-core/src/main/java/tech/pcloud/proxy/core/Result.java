package tech.pcloud.proxy.core;

import lombok.Data;

/**
 * @ClassName Result
 * @Author pandong
 * @Date 2019/1/30 14:40
 **/
@Data
public class Result<T> {
    private int code;
    private T data;
}
