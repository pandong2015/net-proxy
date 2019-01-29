package tech.pcloud.proxy.configure.xml.server.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ServerInfo
 * @Author pandong
 * @Date 2019/1/29 14:28
 **/
@XmlRootElement(name = "Server")
public class ServerInfo {
    private int port;
    private ClientsInfo clients;

    public int getPort() {
        return port;
    }

    @XmlAttribute
    public void setPort(int port) {
        this.port = port;
    }

    public ClientsInfo getClients() {
        return clients;
    }

    @XmlElement(name = "Clients")
    public void setClients(ClientsInfo clients) {
        this.clients = clients;
    }
}
