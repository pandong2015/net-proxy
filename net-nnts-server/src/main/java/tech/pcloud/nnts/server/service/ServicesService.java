package tech.pcloud.nnts.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.server.mapper.NodeServiceRefMapper;
import tech.pcloud.nnts.server.mapper.ServicesMapper;

import java.util.List;


@Slf4j
@Service
public class ServicesService {
    @Autowired
    private ServicesMapper servicesMapper;
    @Autowired
    private NodeServiceRefMapper nodeServiceRefMapper;

    public tech.pcloud.nnts.core.model.Service insert(Node node, tech.pcloud.nnts.core.model.Service service) {
        if(servicesMapper.load(service.getId())==null) {
            servicesMapper.insert(service);
            nodeServiceRefMapper.insert(node.getId(), service.getId());
        }
        return service;
    }

    public List<tech.pcloud.nnts.core.model.Service> selectNodeService(long nodeId) {
        return servicesMapper.selectByNode(nodeId);
    }
}
