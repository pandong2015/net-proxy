package tech.pcloud.nnts.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.pcloud.nnts.client.listeners.ServiceRegisterListener;
import tech.pcloud.nnts.client.mapper.ServicesMapper;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.service.IdGenerateService;

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

    public List<tech.pcloud.nnts.core.model.Service> selectAll() {
        return servicesMapper.selectAll();
    }

    public tech.pcloud.nnts.core.model.Service register(tech.pcloud.nnts.core.model.Service service, Node server) {
        tech.pcloud.nnts.core.model.Service loadService = servicesMapper.loadByName(service.getName());
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
}
