package tech.pcloud.proxy.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.pcloud.proxy.client.listeners.ServiceRegisterListener;
import tech.pcloud.proxy.client.mapper.ServicesMapper;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Status;
import tech.pcloud.proxy.core.service.IdGenerateService;

import java.util.List;

@Slf4j
@Service
public class ServicesService {
    @Autowired
    @Qualifier("client")
    private Node client;
    @Autowired
    private ServicesMapper servicesMapper;
    @Autowired
    private IdGenerateService idGenerateService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ConnectionFactory connectionFactory;

    public List<tech.pcloud.proxy.core.model.Service> selectAll() {
        return servicesMapper.selectAll();
    }

    public tech.pcloud.proxy.core.model.Service register(tech.pcloud.proxy.core.model.Service service, Node server) {
        tech.pcloud.proxy.core.model.Service loadService = servicesMapper.loadByName(service.getName());
        if (loadService == null) {
            service.setId(idGenerateService.generate(IdGenerateService.IdType.SERVICE));
            servicesMapper.insert(service);
        } else {
            servicesMapper.update(service);
        }
        connectionFactory.connect(ConnectionFactory.ConnectType.PROXY, server.getIp(), server.getPort(),
                new ServiceRegisterListener(client, server, messageService));
        return service;
    }

    public void delete(tech.pcloud.proxy.core.model.Service service){
        service.setStatus(Status.OFF.ordinal());
        servicesMapper.updateStatus(service);
    }
}
