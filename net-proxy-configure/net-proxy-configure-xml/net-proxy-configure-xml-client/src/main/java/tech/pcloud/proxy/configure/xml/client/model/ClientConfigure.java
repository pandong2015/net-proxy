package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientConfigure
 * @Author pandong
 * @Date 2019/1/28 15:55
 **/
@XmlRootElement(name = "Configure")
public class ClientConfigure {
    private ServerInfo server;
    private int port;
    private long id;
    private ServicesInfo services;

    public long getId() {
        return id;
    }

    @XmlElement(name = "Id")
    public void setId(long id) {
        this.id = id;
    }

    public int getPort() {
        return port;
    }

    @XmlElement(name = "Port")
    public void setPort(int port) {
        this.port = port;
    }

    public ServicesInfo getServices() {
        return services;
    }

    @XmlElement(name = "Services")
    public void setServices(ServicesInfo services) {
        this.services = services;
    }

    public ServerInfo getServer() {
        return server;
    }

    @XmlElement(name = "Server")
    public void setServer(ServerInfo server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "ClientConfigure{" +
                "server=" + server +
                ", port=" + port +
                ", services=" + services +
                '}';
    }
}
