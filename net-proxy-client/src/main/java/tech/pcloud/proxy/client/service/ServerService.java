package tech.pcloud.proxy.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.proxy.client.mapper.ServersMapper;
import tech.pcloud.proxy.core.model.Node;

@Slf4j
@Service
public class ServerService {
    @Autowired
    private ServersMapper serversMapper;

    public void updateServerByName(Node server){
        serversMapper.updateByName(server);
    }

}
