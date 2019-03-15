package tech.pcloud.proxy.core;

import lombok.Data;
import tech.pcloud.proxy.core.model.BaseEntity;

/**
 * @ClassName Result
 * @Author pandong
 * @Date 2019/1/30 14:40
 **/
@Data
public class Result<T> extends BaseEntity {
    private int code;
    private T data;
    private String message;
}
