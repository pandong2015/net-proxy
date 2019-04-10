package tech.pcloud.proxy.network.client.model;

import com.google.common.collect.Lists;
import lombok.Data;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.network.client.Client;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ClientInfo
 * @Author pandong
 * @Date 2019/2/21 14:23
 **/
@Data
public class ClientInfo {
    private Client client;
    private Server server;
    private int openPort;
    private long id;
    private boolean retry;
    private long sleepTime;
    private List<Service> services = Lists.newArrayList();

    public Service getServiceWithProxyPort(int proxyPort) {
        Optional<Service> optionalService = services.stream()
                .filter(s -> s.getProxyPort() == proxyPort).findFirst();
        if (optionalService == null) {
            return null;
        }
        return optionalService.get();
    }
}
