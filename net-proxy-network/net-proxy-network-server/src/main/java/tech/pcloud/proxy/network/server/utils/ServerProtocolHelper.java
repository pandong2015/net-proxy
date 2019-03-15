package tech.pcloud.proxy.network.server.utils;

import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.ProxyException;
import tech.pcloud.proxy.core.util.ResultGenerate;
import tech.pcloud.proxy.network.core.protocol.ManageProtocolBody;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

/**
 * @ClassName ServerProtocolHelper
 * @Author pandong
 * @Date 2019/2/22 11:19
 **/
public class ServerProtocolHelper {

    public static ProtocolPackage.Protocol createExceptionResponseProtocol(ProxyException e, Service service) {
        ManageProtocolBody body = new ManageProtocolBody();
        body.setCommand(ProtocolCommand.newInstance(NodeType.CLIENT, ProtocolCommand.Command.REGISTER));
        body.setData(ResultGenerate.failResult(e, service));
        return ProtocolHelper.createNormalResponseProtocol(body);
    }

    public static <T> ProtocolPackage.Protocol createRegisterSuccessResponseProtocol(T t) {
        ManageProtocolBody body = new ManageProtocolBody();
        body.setCommand(ProtocolCommand.newInstance(NodeType.CLIENT, ProtocolCommand.Command.REGISTER));
        body.setData(ResultGenerate.successResult(t));
        return ProtocolHelper.createNormalResponseProtocol(body);
    }

    public static ProtocolPackage.Protocol createHeartbeatResponseProtocol() {
        return ProtocolHelper.createHeartbeatResponseProtocol(NodeType.SERVER);
    }
}
