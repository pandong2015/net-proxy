package tech.pcloud.proxy.network.client.utils;

import com.google.common.collect.Maps;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Server;

import java.util.Map;

/**
 * @ClassName ClientCache
 * @Author pandong
 * @Date 2019/2/19 14:57
 **/
public class ClientCache {
    private static final Map<Server, Client> clientServerMapping = Maps.newConcurrentMap();

    public static void addClientServerMapper(Server server, Client client) {
        clientServerMapping.put(server, client);
    }

    public static Client getClientInstance(Server server) {
        if (!clientServerMapping.containsKey(server)) {
            return new Client();
        }
        return clientServerMapping.get(server);
    }
}
