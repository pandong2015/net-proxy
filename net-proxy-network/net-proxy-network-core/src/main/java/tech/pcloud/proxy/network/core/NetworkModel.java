package tech.pcloud.proxy.network.core;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.util.Map;

/**
 * @ClassName NetworkModel
 * @Author pandong
 * @Date 2019/1/29 15:51
 **/
public class NetworkModel implements Model {
    public interface ChannelAttributeName {
        String HEADER = "header";
        String COMMAND = "command";
        String OPERATION = "operation";
        String REQUEST_ID = "requestId";
        String PROXY_SERVER_CHANNEL = "proxyChannel";
        String PROXY_REQUEST_CHANNEL = "requestChannel";
        String SERVER = "server";
        String SERVICE = "service";
        String CLIENT = "client";
        String PORT = "port";
    }

    public interface ChannelAttribute {
        AttributeKey<Map<String, String>> HEADER = AttributeKey.newInstance(ChannelAttributeName.HEADER);
        AttributeKey<ProtocolCommand> COMMAND = AttributeKey.newInstance(ChannelAttributeName.COMMAND);
        AttributeKey<ProtocolPackage.Operation> OPERATION = AttributeKey.newInstance(ChannelAttributeName.OPERATION);
        AttributeKey<Long> REQUEST_ID = AttributeKey.newInstance(ChannelAttributeName.REQUEST_ID);
        AttributeKey<Channel> PROXY_SERVER_CHANNEL = AttributeKey.newInstance(ChannelAttributeName.PROXY_SERVER_CHANNEL);
        AttributeKey<Channel> PROXY_REQUEST_CHANNEL = AttributeKey.newInstance(ChannelAttributeName.PROXY_REQUEST_CHANNEL);
        AttributeKey<Server> SERVER = AttributeKey.newInstance(ChannelAttributeName.SERVER);
        AttributeKey<Service> SERVICE = AttributeKey.newInstance(ChannelAttributeName.SERVICE);
        AttributeKey<Client> CLIENT = AttributeKey.newInstance(ChannelAttributeName.CLIENT);
        AttributeKey<Integer> PORT = AttributeKey.newInstance(ChannelAttributeName.PORT);
    }

    public enum NetworkType {
        SERVER, CLIENT
    }

    private NetworkType type;

    public NetworkModel(NetworkType type) {
        this.type = type;
    }

    @Override
    public int getModelCode() {
        return 300 + type.ordinal() * 10;
    }
}
