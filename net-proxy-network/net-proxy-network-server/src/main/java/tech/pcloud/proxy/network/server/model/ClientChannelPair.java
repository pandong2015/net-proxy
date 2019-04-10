package tech.pcloud.proxy.network.server.model;

import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.model.Service;

import java.util.Map;

/**
 * tech.pcloud.proxy.network.server.model
 * created by pando on 2019/3/15 0015 9:58
 */
@Data
@NoArgsConstructor
public class ClientChannelPair {
    private Client client;
    private Channel channel;
    private Map<String, ServiceStatus> services = Maps.newConcurrentMap();

    public ClientChannelPair(Client client, Channel channel) {
        this.client = client;
        this.channel = channel;
    }

    public ServiceStatus getService(String serviceName) {
        return services.get(serviceName);
    }

    public boolean hasService(String serviceName) {
        return services.containsKey(serviceName);
    }

    public void addService(Service service) {
        services.put(service.getName(), new ServiceStatus(service));
    }
}
