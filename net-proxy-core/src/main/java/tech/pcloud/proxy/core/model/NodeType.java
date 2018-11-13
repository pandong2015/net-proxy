package tech.pcloud.proxy.core.model;

public enum NodeType {
    SERVER(0), CLIENT(1);

    int type;

    NodeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
