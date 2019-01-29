package tech.pcloud.proxy.configure.xml.server.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientInfo
 * @Author pandong
 * @Date 2019/1/29 14:31
 **/
@XmlRootElement(name = "Client")
public class ClientInfo {
    private int port;
    private String host;
    private ServicesInfo services;

    public int getPort() {
        return port;
    }

    @XmlAttribute
    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    @XmlAttribute
    public void setHost(String host) {
        this.host = host;
    }

    public ServicesInfo getServices() {
        return services;
    }

    @XmlElement(name = "Services")
    public void setServices(ServicesInfo services) {
        this.services = services;
    }
}
