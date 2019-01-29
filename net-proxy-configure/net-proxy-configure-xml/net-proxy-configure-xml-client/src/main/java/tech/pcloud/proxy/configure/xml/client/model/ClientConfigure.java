package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientConfigure
 * @Author pandong
 * @Date 2019/1/28 15:55
 **/
@XmlRootElement(name = "Configure")
public class ClientConfigure {
    private ServersInfo servers;
    private ClientInfo client;

    public ServersInfo getServers() {
        return servers;
    }

    @XmlElement(name = "Servers")
    public void setServers(ServersInfo servers) {
        this.servers = servers;
    }

    public ClientInfo getClient() {
        return client;
    }

    @XmlElement(name = "Client")
    public void setClient(ClientInfo client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "ClientConfigure{" +
                "servers=" + servers +
                ", client=" + client +
                '}';
    }
}
