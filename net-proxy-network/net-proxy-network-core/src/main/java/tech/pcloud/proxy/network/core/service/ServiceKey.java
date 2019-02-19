package tech.pcloud.proxy.network.core.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

/**
 * @ClassName ServiceKey
 * @Author pandong
 * @Date 2019/1/30 14:16
 **/
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ServiceKey {
    private int operation;
    private ProtocolPackage.RequestType requestType;
    private NodeType nodeType;
}
