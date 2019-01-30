package tech.pcloud.proxy.network.core;

import io.netty.util.AttributeKey;
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
    }

    public interface ChannelAttribute {
        AttributeKey<Map<String, String>> HEADER = AttributeKey.newInstance(ChannelAttributeName.HEADER);
        AttributeKey<ProtocolCommand> COMMAND = AttributeKey.newInstance(ChannelAttributeName.COMMAND);
        AttributeKey<ProtocolPackage.Operation> OPERATION = AttributeKey.newInstance(ChannelAttributeName.OPERATION);
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
