package tech.pcloud.proxy.configure.xml.server.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ServiceInfo
 * @Author pandong
 * @Date 2019/1/29 14:36
 **/
@XmlRootElement(name = "Service")
public class ServiceInfo {
    private String name;
    private int status;
    private int proxyPort;

    public String getName() {
        return name;
    }

    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    @XmlAttribute
    public void setStatus(int status) {
        this.status = status;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    @XmlAttribute
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
}
