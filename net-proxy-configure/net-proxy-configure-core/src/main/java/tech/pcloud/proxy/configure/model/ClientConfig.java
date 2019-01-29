package tech.pcloud.proxy.configure.model;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ClientConfig
 * @Author pandong
 * @Date 2019/1/29 11:06
 **/
@Data
public class ClientConfig {
    private Client client;
    private List<Server> servers;
}
