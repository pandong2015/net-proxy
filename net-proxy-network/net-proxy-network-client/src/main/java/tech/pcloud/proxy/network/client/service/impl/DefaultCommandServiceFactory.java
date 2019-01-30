package tech.pcloud.proxy.network.client.service.impl;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.network.client.model.ServiceKey;
import tech.pcloud.proxy.network.client.service.CommandService;
import tech.pcloud.proxy.network.client.service.CommandServiceFactory;
import tech.pcloud.proxy.network.client.utils.ReflectionUtil;

import java.util.List;
import java.util.Map;

/**
 * @ClassName DefaultCommandServiceFactory
 * @Author pandong
 * @Date 2019/1/30 15:05
 **/
@Slf4j
public class DefaultCommandServiceFactory implements CommandServiceFactory {
    private Map<ServiceKey, CommandService> serviceMap = Maps.newConcurrentMap();

    public DefaultCommandServiceFactory(String prefix) {
        List<Class<? extends CommandService>> classList = ReflectionUtil.listInterfaceImplements(prefix, CommandService.class);
        classList.forEach(c -> {
            try {
                CommandService service = ReflectionUtil.newInstance(c);
                serviceMap.put(new ServiceKey(service.getRequestType(), service.getNodeType()), service);
            } catch (Exception e) {
                log.warn("create class{} instance fail, {}", c.getName(), e.getMessage());
            }
        });
    }

    @Override
    public CommandService getCommandService(ServiceKey key) {
        return serviceMap.get(key);
    }

}
