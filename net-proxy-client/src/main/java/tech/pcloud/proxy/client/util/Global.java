package tech.pcloud.proxy.client.util;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;

import java.util.Map;

@Slf4j
public class Global {

    public interface ChannelAttributeName {
        String CLIENT = "client";
        String CLIENT_ID = "client_id";
        String PROXY_CHANNEL = "procy_channel";
        String REQUEST_CHANNEL = "request_channel";
        String REQUEST_ID = "request_id";
        String SERVICE = "service";
        String SERVER = "server";
        String DAEMON = "daemon";
    }


    public interface ChannelAttribute {
        AttributeKey<Node> CLIENT = AttributeKey.newInstance(ChannelAttributeName.CLIENT);
        AttributeKey<Long> CLIENT_ID = AttributeKey.newInstance(ChannelAttributeName.CLIENT_ID);
        AttributeKey<Channel> PROXY_CHANNEL = AttributeKey.newInstance(ChannelAttributeName.PROXY_CHANNEL);
        AttributeKey<Channel> REQUEST_CHANNEL = AttributeKey.newInstance(ChannelAttributeName.REQUEST_CHANNEL);
        AttributeKey<Long> REQUEST_ID = AttributeKey.newInstance(ChannelAttributeName.REQUEST_ID);
        AttributeKey<Service> SERVICE = AttributeKey.newInstance(ChannelAttributeName.SERVICE);
        AttributeKey<Node> SERVER = AttributeKey.newInstance(ChannelAttributeName.SERVER);
        AttributeKey<Boolean> DAEMON = AttributeKey.newInstance(ChannelAttributeName.DAEMON);
    }


    public enum ClientConnectStatus {
        SHUTDOWN, CONNECT
    }

    private static Map<Long, ClientConnectStatus> connectStatus = Maps.newConcurrentMap();

    public static void setConnectStatus(Node server, ClientConnectStatus clientConnectStatus) {
        connectStatus.put(server.getId(), clientConnectStatus);
    }


    public static ClientConnectStatus getConnectStatus(Node server) {
        return connectStatus.get(server.getId());
    }

}
