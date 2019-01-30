package tech.pcloud.proxy.network.core.protocol;

/**
 * @ClassName Operation
 * @Author pandong
 * @Date 2019/1/25 14:32
 **/
public enum Operation {
    NORMAL, HEARTBEAT, TRANSFER;

    public int getOperation() {
        return this.ordinal();
    }

    public static Operation getOperation(int operation) {
        return Operation.values()[operation % Operation.values().length];
    }
}
