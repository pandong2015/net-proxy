package tech.pcloud.proxy.network.core.protocol;

/**
 * @ClassName Operation
 * @Author pandong
 * @Date 2019/1/25 14:32
 **/
public enum Operation {
    NORMAL(0), HEARTBEAT(1), TRANSFER(2);

    int operation;

    Operation(int operation) {
        this.operation = operation;
    }

    public int getOperation() {
        return operation;
    }}
