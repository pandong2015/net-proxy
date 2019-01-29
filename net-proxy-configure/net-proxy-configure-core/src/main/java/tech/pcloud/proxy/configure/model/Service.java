package tech.pcloud.proxy.configure.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName Service
 * @Author pandong
 * @Date 2019/1/25 13:31
 **/
@Data
public class Service extends Node {
    private String name;
    private int status;
    private int proxyPort;
    private List<ProxyTarget> targets;

    public Service() {
        super(NodeType.SERVICE);
    }
}
