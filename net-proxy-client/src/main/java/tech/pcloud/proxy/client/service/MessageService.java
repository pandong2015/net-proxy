package tech.pcloud.proxy.client.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tech.pcloud.proxy.core.model.Client;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.service.IdGenerateService;
import tech.pcloud.proxy.core.util.MessageGenerate;
import tech.pcloud.proxy.message.TransferProto;

import java.util.List;

@Slf4j
@Service
public class MessageService {
    @Autowired
    @Qualifier("client")
    private Node client;
    @Autowired
    private IdGenerateService idGenerateService;
    @Autowired
    private ServicesService servicesService;

    public TransferProto.Transfer generateServiceShutdown(Node node, tech.pcloud.proxy.core.model.Service service) {
        return MessageGenerate.generateServiceShutdownMessage(idGenerateService.generate(IdGenerateService.IdType.REQUEST),
                node.getId(), service, TransferProto.RequestType.REQUEST);
    }

    public TransferProto.Transfer generateRegistre(Node node) {
        List<tech.pcloud.proxy.core.model.Service> services = servicesService.selectAll();
        Client client = new Client(node);
        client.setServices(services);
        return MessageGenerate.generateClientRegistreMessage(idGenerateService.generate(IdGenerateService.IdType.REQUEST),
                client, client, TransferProto.RequestType.REQUEST);
    }

    public TransferProto.Transfer generateHeartbeat() {
        return MessageGenerate.generateHeartbeatMessage(idGenerateService.generate(IdGenerateService.IdType.REQUEST),
                client.getId(), TransferProto.RequestType.REQUEST);
    }

    public TransferProto.Transfer generateConnect(long requestId, tech.pcloud.proxy.core.model.Service service) {
        return MessageGenerate.generateConnectMessage(requestId, client, service, TransferProto.RequestType.RESPONSE);
    }

    public TransferProto.Transfer generateDisconnect(long requestId, tech.pcloud.proxy.core.model.Service service) {
        return MessageGenerate.generateDisconnectMessage(requestId, client, service, TransferProto.RequestType.REQUEST);
    }

    public TransferProto.Transfer generateRegistreService(tech.pcloud.proxy.core.model.Service service) {
        return MessageGenerate.generateServiceRegistreMessage(
                idGenerateService.generate(IdGenerateService.IdType.REQUEST),
                client, service, TransferProto.RequestType.REQUEST);
    }

    public TransferProto.Transfer generateTransfer(long requestId, tech.pcloud.proxy.core.model.Service service, byte[] data) {
        return MessageGenerate.generateTransferMessage(requestId, client, service, data, TransferProto.RequestType.REQUEST);
    }
}
