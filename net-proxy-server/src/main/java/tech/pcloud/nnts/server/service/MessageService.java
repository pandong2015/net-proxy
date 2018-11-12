package tech.pcloud.nnts.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.pcloud.nnts.core.model.Client;
import tech.pcloud.nnts.core.model.Node;
import tech.pcloud.nnts.core.util.MessageGenerate;
import tech.pcloud.nnts.message.TransferProto;

@Slf4j
@Service
public class MessageService {
    public TransferProto.Transfer generateHeartbeat(TransferProto.Transfer request, Node server) {
        return MessageGenerate.generateHeartbeatMessage(request.getRequestId(), server.getId(), TransferProto.RequestType.RESPONSE);
    }

    public TransferProto.Transfer generateClientRegistre(TransferProto.Transfer request, Node node, Node Server) {
        return MessageGenerate.generateClientRegistreMessage(request.getRequestId(), node, Server, TransferProto.RequestType.RESPONSE);
    }

    public TransferProto.Transfer generateRegistreService(TransferProto.Transfer request, long nodeId, tech.pcloud.nnts.core.model.Service service) {
        return MessageGenerate.generateServiceRegistreMessage(request.getRequestId(), nodeId, service, TransferProto.RequestType.RESPONSE);

    }

    public TransferProto.Transfer generateTransfer(long requestId, Node node, tech.pcloud.nnts.core.model.Service service, byte[] data) {
        return MessageGenerate.generateTransferMessage(requestId, node, service, data, TransferProto.RequestType.RESPONSE);
    }

    public TransferProto.Transfer generateConnectRequest(long requestId, Node node, tech.pcloud.nnts.core.model.Service service) {
        return MessageGenerate.generateConnectMessage(requestId, node, service, TransferProto.RequestType.REQUEST);
    }

    public TransferProto.Transfer generateDisconnectRequest(long requestId, Node node, tech.pcloud.nnts.core.model.Service service) {
        return MessageGenerate.generateDisconnectMessage(requestId, node, service, TransferProto.RequestType.REQUEST);
    }
}
