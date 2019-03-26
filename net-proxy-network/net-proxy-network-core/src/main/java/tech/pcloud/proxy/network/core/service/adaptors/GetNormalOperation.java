package tech.pcloud.proxy.network.core.service.adaptors;

import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.service.GetOperation;

/**
 * tech.pcloud.proxy.network.core.service.adaptors
 * created by pando on 2019/3/12 0012 18:06
 */
public interface GetNormalOperation extends GetOperation {
    @Override
    default int getOperation() {
        return Operation.NORMAL.getOperation();
    }
}