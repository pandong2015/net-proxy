package tech.pcloud.proxy.network.server.model;

import io.netty.channel.Channel;
import lombok.Data;
import tech.pcloud.proxy.configure.model.Service;

/**
 * tech.pcloud.proxy.network.server.model
 * created by pando on 2019/3/15 0015 10:42
 */
@Data
public class ServiceStatus {
    private Service service;
    private boolean isOpenProxyPort;
    private Channel proxyChannel;

    public ServiceStatus(Service service) {
        this.service = service;
    }
}
