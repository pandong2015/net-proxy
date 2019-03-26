package tech.pcloud.proxy.network.core.protocol;

/**
 * @ClassName Operation
 * @Author pandong
 * @Date 2019/1/25 14:32
 **/
public enum Operation {
    NORMAL, // 管理模式
    HEARTBEAT, // 心跳模式
    TRANSFER, // 传输模式
    TRANSFER_DISCONNECT, //传输关闭
    TRANSFER_REQUEST; // 请求模式，用于传输前，通知客户端

    public int getOperation() {
        return this.ordinal();
    }

    public static Operation getOperation(int operation) {
        return Operation.values()[operation % Operation.values().length];
    }
}
