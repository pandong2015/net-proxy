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
    //    private List<Client> clients;
    private int masterPoolSize = 5;
    private int workerPoolSize = 200;

    public Server() {
        super(NodeType.SERVER);
    }
}
