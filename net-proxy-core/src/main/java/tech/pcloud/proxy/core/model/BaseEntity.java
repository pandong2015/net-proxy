package tech.pcloud.proxy.core.model;

import lombok.Data;
import tech.pcloud.framework.utility.common.HashUtil;

@Data
public class BaseEntity extends BaseObject implements ToHash {
    private String name;

    @Override
    public long toHash() {
        return HashUtil.hashByMD5(name);
    }
}
