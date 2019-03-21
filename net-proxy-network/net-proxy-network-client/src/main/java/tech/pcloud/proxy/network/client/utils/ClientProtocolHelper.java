package tech.pcloud.proxy.network.client.utils;

import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.NodeType;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.model.ToJson;
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

    public static <T extends ToJson> ManageProtocolBody createRegistorRequestBody(NodeType nodeType, T t){
        ManageProtocolBody<T> body = new ManageProtocolBody();
        body.setCommand(ProtocolCommand.newInstance(nodeType, ProtocolCommand.Command.REGISTER));
        body.setData(t);
        return body;
    }

    public static <T extends ToJson> ProtocolPackage.Protocol createClientRegistorRequestBody(NodeType nodeType, T t){
        ManageProtocolBody<T> body = createRegistorRequestBody(nodeType, t);
        return ProtocolHelper.createNormalRequestProtocol(body);
    }

    public static ProtocolPackage.Protocol createRegisterServiceRequestProtocol(Service service){
        return createClientRegistorRequestBody(NodeType.SERVICE, service);
    }

    public static ProtocolPackage.Protocol createRegisterClientRequestProtocol(Client client) {
        return createClientRegistorRequestBody(NodeType.CLIENT, client);
    }

    public static ProtocolPackage.Protocol createHeartBeatRequestProtocol() {
        return ProtocolHelper.createHeartBeatRequestProtocol(NodeType.CLIENT);
    }
}
