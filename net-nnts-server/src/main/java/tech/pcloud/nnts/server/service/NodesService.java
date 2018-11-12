package tech.pcloud.nnts.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.pcloud.nnts.core.model.Client;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.model.NodeType;
import tech.pcloud.nnts.server.mapper.NodesMapper;

import java.util.List;

@Slf4j
@Service
public class NodesService {
    @Autowired
    private NodesMapper nodesMapper;


    public Client insert(Client client, NodeType type) {
        client.setType(type.getType());
        Node node = nodesMapper.load(client.getId());
        if (node == null) {
            nodesMapper.insert(client);
        }
        return client;
    }

    public Node load(long id) {
        return nodesMapper.load(id);
    }

    public List<Node> selectAll() {
        return nodesMapper.selectAll();
    }
}
