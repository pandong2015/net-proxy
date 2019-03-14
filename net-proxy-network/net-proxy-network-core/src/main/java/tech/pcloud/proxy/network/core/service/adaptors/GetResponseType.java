package tech.pcloud.proxy.network.core.service.adaptors;

import tech.pcloud.proxy.network.core.service.GetIOType;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

/**
 * tech.pcloud.proxy.network.core.service.adaptors
 * created by pando on 2019/3/12 0012 18:02
 */
public interface GetResponseType extends GetIOType {
    @Override
    default ProtocolPackage.RequestType getRequestType() {
        return ProtocolPackage.RequestType.RESPONSE;
    }
}
