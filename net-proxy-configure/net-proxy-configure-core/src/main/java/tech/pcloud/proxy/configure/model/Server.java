package tech.pcloud.proxy.configure.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName Server
 * @Author pandong
 * @Date 2019/1/25 11:50
 **/
@Data
@EqualsAndHashCode
public class Server extends Node {
    private List<Client> clients;

    public Server() {
        super(NodeType.SERVER);
    }
}
