package tech.pcloud.nnts.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.nnts.client.mapper.ServersMapper;
import tech.pcloud.nnts.core.model.Node;

@Slf4j
@Service
public class ServerService {
    @Autowired
    private ServersMapper serversMapper;

    public void updateServerByName(Node server){
        serversMapper.updateByName(server);
    }

}
