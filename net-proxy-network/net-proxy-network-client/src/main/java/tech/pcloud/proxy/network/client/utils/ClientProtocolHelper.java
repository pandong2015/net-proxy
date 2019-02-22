package tech.pcloud.proxy.network.client.utils;

import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.network.core.protocol.ManageProtocolBody;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.core.utils.ProtocolHelper;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

/**
 * @ClassName ClientProtocolHelper
 * @Author pandong
 * @Date 2019/2/22 11:16
 **/
public class ClientProtocolHelper {
    public static ProtocolPackage.Protocol createRegisterClientRequestProtocol(Client client) {
        ManageProtocolBody body = new ManageProtocolBody();
        body.setCommand(ProtocolCommand.newInstance(NodeType.CLIENT, ProtocolCommand.Command.REGISTER));
        body.setData(client);
        return ProtocolHelper.createNormalRequestProtocol(body);
    }

    public static ProtocolPackage.Protocol createHeartBeatRequestProtocol() {
        return ProtocolHelper.createHeartBeatRequestProtocol(NodeType.CLIENT);
    }
}
