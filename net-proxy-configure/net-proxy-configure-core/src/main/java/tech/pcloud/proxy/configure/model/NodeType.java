package tech.pcloud.proxy.configure.model;

public enum NodeType {
    SERVER(0), CLIENT(1), SERVICE(2);

    int type;

    NodeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
