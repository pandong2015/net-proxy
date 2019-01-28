package tech.pcloud.proxy.configure.model;

import lombok.Data;

/**
 * @ClassName Node
 * @Author pandong
 * @Date 2019/1/25 11:45
 **/
@Data
public class Node extends BaseObject{
    private String host;
    private int port;
    private NodeType type;

    public Node(NodeType type) {
        this.type = type;
    }
}
