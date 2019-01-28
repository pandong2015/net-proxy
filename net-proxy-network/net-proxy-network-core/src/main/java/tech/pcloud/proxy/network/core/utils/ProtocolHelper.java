package tech.pcloud.proxy.network.core.utils;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import tech.pcloud.proxy.network.core.exceptions.ProtocolException;
import tech.pcloud.proxy.network.core.protocol.Operation;
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

    public static ProtocolPackage.Protocol createTransferRequestProtocol(byte[] body) {
        return createRequestProtocol(Operation.TRANSFER.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createNormalRequestProtocol(byte[] body) {
        return createRequestProtocol(Operation.NORMAL.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createHeartBeatRequestProtocol(byte[] body) {
        return createRequestProtocol(Operation.HEARTBEAT.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createTransferRequestProtocol(String body) {
        return createRequestProtocol(Operation.TRANSFER.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createNormalRequestProtocol(String body) {
        return createRequestProtocol(Operation.NORMAL.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createHeartBeatRequestProtocol(String body) {
        return createRequestProtocol(Operation.HEARTBEAT.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createRequestProtocol(int operation, String body) {
        return createRequestProtocol(operation, null, body.getBytes(Charset.forName("UTF-8")));
    }

    public static ProtocolPackage.Protocol createRequestProtocol(int operation, byte[] body) {
        return createRequestProtocol(operation, null, body);
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

    public static ProtocolPackage.Protocol createHeartbeatResponseProtocol(byte[] body) {
        return createResponseProtocol(Operation.HEARTBEAT.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createTransferResponseProtocol(String body) {
        return createResponseProtocol(Operation.TRANSFER.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createNormalResponseProtocol(String body) {
        return createResponseProtocol(Operation.NORMAL.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createHeartbeatResponseProtocol(String body) {
        return createResponseProtocol(Operation.HEARTBEAT.getOperation(), body);
    }

    public static ProtocolPackage.Protocol createResponseProtocol(int operation, String body) {
        return createResponseProtocol(operation, null, body.getBytes(Charset.forName("UTF-8")));
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
        return ProtocolPackage.Protocol.newBuilder()
                .setVersion(version)
                .setOperation(ProtocolPackage.Operation.newBuilder()
                        .setOperation(operation)
                        .setTypeValue(operationType)
                        .build())
                .putAllHeaders(headers)
                .setBody(ByteString.copyFrom(body))
                .build();
    }

    public static ProtocolPackage.Protocol parse(byte[] data) {
        try {
            return ProtocolPackage.Protocol.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            throw new ProtocolException("protocol parse fail, " + e.getMessage(), e, 11000);
        }
    }
}
