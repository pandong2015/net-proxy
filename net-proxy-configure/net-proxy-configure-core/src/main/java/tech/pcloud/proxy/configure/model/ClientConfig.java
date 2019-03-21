package tech.pcloud.proxy.configure.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName ClientConfig
 * @Author pandong
 * @Date 2019/1/29 11:06
 **/
@Data
public class ClientConfig {
    private long id;
    private int port;
    private List<Service> services;
    private Server server;

    public List<Service> getServices(Server server) {
        if (services == null || services.isEmpty()) {
            return Lists.newArrayList();
        }
        Map<String, Set<Service>> serverListMap = Maps.newHashMap();
        services.stream().filter(Service::isDeploy).forEach(s -> {
            s.getTargets().forEach(t -> {
                Set<Service> serviceSet = serverListMap.computeIfAbsent(t.getServerName(), f -> Sets.newHashSet());
                serviceSet.add(s);
            });
        });
        if (serverListMap.containsKey(server.getName())) {
            return Lists.newArrayList(serverListMap.get(server.getName()));
        }
        return Lists.newArrayList();
    }
}
