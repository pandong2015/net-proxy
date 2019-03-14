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

    public static void mappingClientInfoWithPort(int port, ClientInfo clientInfo){
        clientInfo.setOpenPort(port);
        portServerMapping.put(port, clientInfo);
    }

    public static ClientInfo getClientInfoWithPort(int port) {
        return portServerMapping.get(port);
    }
}
