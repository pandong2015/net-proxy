package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ServiceHostInfo
 * @Author pandong
 * @Date 2019/1/28 16:26
 **/
@XmlRootElement(name = "Node")
public class ServiceHostInfo {
    private String host;
    private int port;

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
        return "ServiceHostInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
