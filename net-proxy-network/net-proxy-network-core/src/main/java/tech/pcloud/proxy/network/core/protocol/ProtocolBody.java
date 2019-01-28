package tech.pcloud.proxy.network.core.protocol;

import lombok.Data;

/**
 * @ClassName ProtocolBody
 * @Author pandong
 * @Date 2019/1/25 15:41
 **/
@Data
public class ProtocolBody<T> {
    private ProtocolCommand command;
    private T data;
}
