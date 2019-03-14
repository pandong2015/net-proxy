package tech.pcloud.proxy.configure.model;

import lombok.Data;
import tech.pcloud.proxy.core.model.BaseEntity;

/**
 * @ClassName ProxyTarget
 * @Author pandong
 * @Date 2019/1/25 13:32
 **/
@Data
public class ProxyTarget extends BaseEntity {
    private String serverName;
    private Status status;
}
