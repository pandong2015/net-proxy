package tech.pcloud.proxy.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Status;
import tech.pcloud.proxy.server.mapper.NodeServiceRefMapper;
import tech.pcloud.proxy.server.mapper.ServicesMapper;

import java.util.List;


@Slf4j
@Service
public class ServicesService {
    @Autowired
    private ServicesMapper servicesMapper;
    @Autowired
    private NodeServiceRefMapper nodeServiceRefMapper;

    public tech.pcloud.proxy.core.model.Service insert(Node node, tech.pcloud.proxy.core.model.Service service) {
        if(servicesMapper.load(service.getId())==null) {
            servicesMapper.insert(service);
            nodeServiceRefMapper.insert(node.getId(), service.getId());
        }
        return service;
    }

    public List<tech.pcloud.proxy.core.model.Service> selectNodeService(long nodeId) {
        return servicesMapper.selectByNode(nodeId);
    }

    public void delete(tech.pcloud.proxy.core.model.Service service){
        service.setStatus(Status.OFF.ordinal());
        servicesMapper.updateStatus(service);
    }
}
