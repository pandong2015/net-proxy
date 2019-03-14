package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ClientServer
 * @Author pandong
 * @Date 2019/1/28 16:06
 **/
@XmlRootElement(name = "Server")
public class ServerInfo {
    private String host;
    private int port;
    private String name;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    @XmlAttribute
    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    @XmlAttribute
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
