package tech.pcloud.proxy.core.model;

import lombok.Data;

@Data
public class Node extends BaseEntity {
    private Long id;
    private String ip;
    private Integer port;
    private Integer type = NodeType.CLIENT.getType();

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", type=" + type +
                '}';
    }

}
