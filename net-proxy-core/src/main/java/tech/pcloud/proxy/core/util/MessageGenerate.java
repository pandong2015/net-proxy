package tech.pcloud.proxy.core.util;

import com.google.protobuf.ByteString;
import tech.pcloud.proxy.core.model.Node;
import tech.pcloud.proxy.core.model.Service;
import tech.pcloud.proxy.core.model.Services;
import tech.pcloud.proxy.message.TransferProto;


public class MessageGenerate {
    public static TransferProto.Transfer generateHeartbeatMessage(long requestId, Node node,
                                                                  TransferProto.RequestType type) {
        return generateHeartbeatMessage(requestId, node.getId(), type);
    }

    public static TransferProto.Transfer generateHeartbeatMessage(long requestId, long nodeId,
                                                                  TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.HEARTBEAT)
                .setNodeId(nodeId)
                .setRequestId(requestId)
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateClientRegistreMessage(long requestId, Node node, Node server,
                                                                       TransferProto.RequestType type) {
        return generateClientRegistreMessage(requestId, node.getId(), server, type);
    }

    public static TransferProto.Transfer generateClientRegistreMessage(long requestId, long nodeId, Node server,
                                                                       TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.CLIENT_REGISTER)
                .setNodeId(nodeId)
                .setRequestId(requestId)
                .setData(ByteString.copyFromUtf8(server.toJson()))
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateServiceRegistreMessage(long requestId, Node node, Service service,
                                                                        TransferProto.RequestType type) {
        return generateServiceRegistreMessage(requestId, node.getId(), service, type);
    }

    public static TransferProto.Transfer generateServiceRegistreMessage(long requestId, long nodeId, Service service,
                                                                        TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.SERVICE_REGISTER)
                .setNodeId(nodeId)
                .setRequestId(requestId)
                .setServiceName(service.getName())
                .setData(ByteString.copyFromUtf8(service.toJson()))
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateServiceShutdownMessage(long requestId, long nodeId, Service service,
                                                                        TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.SERVICE_SHUTDOWN)
                .setNodeId(nodeId)
                .setRequestId(requestId)
                .setServiceName(service.getName())
                .setData(ByteString.copyFromUtf8(service.toJson()))
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateListServiceMessage(long requestId, long serverId, Services services,
                                                                        TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.SERVICE_SHUTDOWN)
                .setNodeId(serverId)
                .setRequestId(requestId)
                .setData(ByteString.copyFromUtf8(services.toJson()))
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateConnectMessage(long requestId, Node node, Service service,
                                                                TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.CONNECT)
                .setNodeId(node.getId())
                .setRequestId(requestId)
                .setServiceName(service.getName())
                .setData(ByteString.copyFromUtf8(service.toJson()))
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateDisconnectMessage(long requestId, Node node, Service service,
                                                                   TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.DISCONNECT)
                .setNodeId(node.getId())
                .setRequestId(requestId)
                .setServiceName(service.getName())
                .setData(ByteString.copyFromUtf8(service.toJson()))
                .setType(type)
                .build();
    }

    public static TransferProto.Transfer generateTransferMessage(long requestId, Node node, Service service,
                                                                 byte[] data, TransferProto.RequestType type) {
        return TransferProto.Transfer.newBuilder()
                .setOperation(TransferProto.OperationType.TRANSFER)
                .setNodeId(node.getId())
                .setRequestId(requestId)
                .setServiceName(service.getName())
                .setData(ByteString.copyFrom(data))
                .setType(type)
                .build();
    }
}
