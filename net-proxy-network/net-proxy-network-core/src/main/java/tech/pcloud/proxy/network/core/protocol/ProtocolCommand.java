package tech.pcloud.proxy.network.core.protocol;

import com.google.common.collect.Maps;
import lombok.Data;
import tech.pcloud.proxy.configure.model.NodeType;

import java.util.Map;

/**
 * @ClassName ProtocolBody
 * @Author pandong
 * @Date 2019/1/25 15:15
 **/
@Data
public class ProtocolCommand {
    public enum CommandType {
        MANAGE, TRANSFER;
    }

    public enum Command {
        REGISTER, CONNECT, DISCONNECT, LIST, SHUTDOWN;
    }

    public static final String PROTOCOL_HEADER_NAME_COMMANDTYPE = "X-COMMAND-TYPE";
    public static final String PROTOCOL_HEADER_NAME_NODE_TYPE = "X-NODE-TYPE";
    public static final String PROTOCOL_HEADER_NAME_COMMAND = "X-COMMAND-CONTENT";

    private CommandType commandType;
    private NodeType nodeType;
    private Command command;

    public Map<String, String> getHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(PROTOCOL_HEADER_NAME_COMMANDTYPE, commandType.name());
        headers.put(PROTOCOL_HEADER_NAME_NODE_TYPE, nodeType.name());
        headers.put(PROTOCOL_HEADER_NAME_COMMAND, command.name());
        return headers;
    }
}
