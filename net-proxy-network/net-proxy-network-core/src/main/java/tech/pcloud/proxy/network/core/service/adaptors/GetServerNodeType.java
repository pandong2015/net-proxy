package tech.pcloud.proxy.network.core.service.adaptors;

import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.network.core.service.GetNodeType;

/**
 * tech.pcloud.proxy.network.core.service.adaptors
 * created by pando on 2019/3/12 0012 17:58
 */
public interface GetServerNodeType extends GetNodeType {
    @Override
    default NodeType getNodeType(){
        return NodeType.SERVER;
    }
}
