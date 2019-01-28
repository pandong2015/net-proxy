package tech.pcloud.proxy.configure.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Client
 * @Author pandong
 * @Date 2019/1/25 13:28
 **/
@Data
public class Client extends Node {
    private List<Service> services;

    public Client() {
        super(NodeType.CLIENT);
    }
}
