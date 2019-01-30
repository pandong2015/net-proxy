package tech.pcloud.proxy.network.core.utils;

import com.google.protobuf.ByteString;
import tech.pcloud.proxy.configure.model.ToJson;
import tech.pcloud.proxy.network.core.protocol.ManageProtocolBody;
import tech.pcloud.proxy.network.core.protocol.Operation;
import tech.pcloud.proxy.network.core.protocol.ProtocolCommand;
import tech.pcloud.proxy.network.protocol.ProtocolPackage;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @ClassName ProtocolHelper
 * @Author pandong
 * @Date 2019/1/25 14:20
 **/
public class ProtocolHelper {
    public static final String PROTOCOL_VERSION = "1.0";

    public static ProtocolPackage.Protocol createNormalRequestProtocol(ManageProtocolBody body) {
        return createNormalRequestProtocol(body.getCommand(), ((ToJson) body.getData()).toJson());
    }

    public static ProtocolPackage.Protocol createTransferRequestProtocol(Map<String, String> headers, byte[] body) {
        return createRequestProtocol(Operation.TRANSFER.getOperation(), headers, body);
    }

    public static ProtocolPackage.Protocol createHeartBeatRequestProtocol() {
        return createRequestProtocol(Operation.HEARTBEAT.getOperation(), null, (String) null);
    }

    public static ProtocolPackage.Protocol createNormalRequestProtocol(ProtocolCommand command, String body) {
        return createRequestProtocol(Operation.NORMAL.getOperation(), command.getHeaders(), body);
    }

    public static ProtocolPackage.Protocol createRequestProtocol(int operation, Map<String, String> headers, String body) {
        byte[] bytes = null;
        if (body != null) {
            bytes = body.getBytes(Charset.forName("UTF-8"));
        }
        return createRequestProtocol(operation, headers, bytes);
    }

    public static ProtocolPackage.Protocol createRequestProtocol(int operation, Map<String, String> headers, byte[] body) {
        return createProtocol(operation, ProtocolPackage.RequestType.REQUEST_VALUE, headers, body);
    }

    public static ProtocolPackage.Protocol createTransferResponseProtocol(byte[] body) {
        return createResponseProtocol(Operation.TRANSFER.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createNormalResponseProtocol(byte[] body) {
        return createResponseProtocol(Operation.NORMAL.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createNormalResponseProtocol(String body) {
        return createResponseProtocol(Operation.NORMAL.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createHeartbeatResponseProtocol() {
        return createResponseProtocol(Operation.HEARTBEAT.getOperation(), (String) null);
    }

    public static ProtocolPackage.Protocol createResponseProtocol(int operation, String body) {
        byte[] bytes = null;
        if (body != null) {
            bytes = body.getBytes(Charset.forName("UTF-8"));
        }
        return createResponseProtocol(operation, null, bytes);
    }

    public static ProtocolPackage.Protocol createResponseProtocol(int operation, byte[] body) {
        return createResponseProtocol(operation, null, body);
    }

    public static ProtocolPackage.Protocol createResponseProtocol(int operation, Map<String, String> headers, byte[] body) {
        return createProtocol(operation, ProtocolPackage.RequestType.RESPONSE_VALUE, headers, body);
    }

    public static ProtocolPackage.Protocol createProtocol(int operation, int operationType,
                                                          Map<String, String> headers, byte[] body) {
        return createProtocol(PROTOCOL_VERSION, operation, operationType, headers, body);
    }

    public static ProtocolPackage.Protocol createProtocol(String version, int operation, int operationType,
                                                          Map<String, String> headers, byte[] body) {
        ProtocolPackage.Protocol.Builder builder = ProtocolPackage.Protocol.newBuilder();
        builder.setVersion(version)
                .setOperation(ProtocolPackage.Operation.newBuilder()
                        .setOperation(operation)
                        .setTypeValue(operationType)
                        .build())
                .putAllHeaders(headers);
        if (body != null) {
            builder.setBody(ByteString.copyFrom(body));
        }
        return builder.build();
    }

    public static ProtocolPackage.Protocol parse(byte[] data) throws Exception {
        try {
            return ProtocolPackage.Protocol.parseFrom(data);
        } catch (Exception e) {
            throw e;
        }
    }
}
