package tech.pcloud.proxy.network.client.utils;

import com.google.common.collect.Maps;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.network.client.Client;
import tech.pcloud.proxy.network.client.model.ClientInfo;

import java.util.Map;

/**
 * @ClassName ClientCache
 * @Author pandong
 * @Date 2019/2/19 14:57
 **/
public class ClientCache {
    private static final Map<Integer, ClientInfo> portServerMapping = Maps.newConcurrentMap();

    public static void mappingClientPortAndServer(int port, Server server, Client client) {
        ClientInfo clientInfo = portServerMapping.computeIfAbsent(port, f -> new ClientInfo());
        clientInfo.setClient(client);
        clientInfo.setServer(server);
        clientInfo.setOpenPort(port);
    }

    public static ClientInfo getClientInfoWithPort(int port) {
        return portServerMapping.get(port);
    }
}
