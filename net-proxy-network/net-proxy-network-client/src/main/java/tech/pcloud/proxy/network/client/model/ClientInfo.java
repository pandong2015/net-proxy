package tech.pcloud.proxy.network.client.model;

import lombok.Data;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.network.client.Client;

/**
 * @ClassName ClientInfo
 * @Author pandong
 * @Date 2019/2/21 14:23
 **/
@Data
public class ClientInfo {
    private Client client;
    private Server server;
    private int openPort;
    private long id;
}
