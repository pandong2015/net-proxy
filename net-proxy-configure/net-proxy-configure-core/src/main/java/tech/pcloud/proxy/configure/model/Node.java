package tech.pcloud.proxy.configure.model;

import lombok.Data;
import tech.pcloud.proxy.core.model.BaseEntity;

/**
 * @ClassName Node
 * @Author pandong
 * @Date 2019/1/25 11:45
 **/
@Data
public class Node extends BaseEntity {
    private Long id;
    private String host;
    private int port;
    private NodeType type;

    public Node(NodeType type) {
        this.type = type;
    }
}
