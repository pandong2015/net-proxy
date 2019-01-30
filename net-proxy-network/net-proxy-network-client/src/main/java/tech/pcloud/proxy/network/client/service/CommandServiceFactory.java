package tech.pcloud.proxy.network.client.service;

import tech.pcloud.proxy.network.client.model.ServiceKey;

/**
 * @ClassName CommandServiceFactory
 * @Author pandong
 * @Date 2019/1/30 14:26
 **/
public interface CommandServiceFactory {
    CommandService getCommandService(ServiceKey key);
}
