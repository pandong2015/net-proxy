package tech.pcloud.nnts.client.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tech.pcloud.nnts.client.listeners.ServiceRegisterListener;
import tech.pcloud.nnts.client.mapper.ServersMapper;
import tech.pcloud.nnts.client.util.Global;
import tech.pcloud.nnts.core.model.Node;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class RegistreService {
    @Autowired
    @Qualifier("client")
    private Node client;
    @Autowired
    private ServersMapper serversMapper;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private MessageService messageService;

    @Scheduled(fixedRate = 60000)
//    @PostConstruct
    public void registre() {
        List<Node> servers = serversMapper.selectAll();
        servers.forEach(server -> {
            Global.ClientConnectStatus status = Global.getConnectStatus(server);
            if (status == null || status == Global.ClientConnectStatus.SHUTDOWN) {
                connectionFactory.connect(ConnectionFactory.ConnectType.PROXY, server.getIp(), server.getPort(),
                        new ServiceRegisterListener(client, server, messageService));
            }
        });

    }
}
