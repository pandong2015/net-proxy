package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientInfo
 * @Author pandong
 * @Date 2019/1/28 16:17
 **/
@XmlRootElement(name = "Client")
public class ClientInfo {
    private int port;
    private ServicesInfo services;

    public int getPort() {
        return port;
    }

    @XmlAttribute
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

    @Override
    public String toString() {
        return "ClientInfo{" +
                "port=" + port +
                ", services=" + services +
                '}';
    }
}
