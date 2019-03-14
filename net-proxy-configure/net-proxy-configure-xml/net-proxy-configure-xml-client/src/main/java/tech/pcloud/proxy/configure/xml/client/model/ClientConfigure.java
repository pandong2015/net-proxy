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
    private ServersInfo servers;
    private int port;
    private ServicesInfo services;

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

    public ServersInfo getServers() {
        return servers;
    }

    @XmlElement(name = "Servers")
    public void setServers(ServersInfo servers) {
        this.servers = servers;
    }

    @Override
    public String toString() {
        return "ClientConfigure{" +
                "servers=" + servers +
                ", port=" + port +
                ", services=" + services +
                '}';
    }
}
