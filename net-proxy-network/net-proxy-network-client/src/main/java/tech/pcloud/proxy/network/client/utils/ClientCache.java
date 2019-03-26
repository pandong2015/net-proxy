package tech.pcloud.proxy.network.client.utils;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import tech.pcloud.proxy.network.client.model.ClientInfo;

import java.util.Map;

/**
 * @ClassName ClientCache
 * @Author pandong
 * @Date 2019/2/19 14:57
 **/
public class ClientCache {
    private static final Map<Integer, ClientInfo> portServerMapping = Maps.newConcurrentMap();
    private static final Map<Long, Channel> serviceChannelMapping = Maps.newConcurrentMap();

    public static void cacheServiceChannel(long requestId, Channel channel) {
        serviceChannelMapping.put(requestId, channel);
    }

    public static Channel getServiceChannel(long requestId) {
        return serviceChannelMapping.get(requestId);
    }

    public static void removeServiceChannel(long requestId) {
        serviceChannelMapping.remove(requestId);
    }

    public static void mappingClientInfoWithPort(int port, ClientInfo clientInfo) {
        clientInfo.setOpenPort(port);
        portServerMapping.put(port, clientInfo);
    }

    public static ClientInfo getClientInfoWithPort(int port) {
        return portServerMapping.get(port);
    }
}
