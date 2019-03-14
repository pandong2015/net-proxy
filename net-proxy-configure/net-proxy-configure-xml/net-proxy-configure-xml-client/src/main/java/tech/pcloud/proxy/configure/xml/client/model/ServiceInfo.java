package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ServiceInfo
 * @Author pandong
 * @Date 2019/1/28 16:24
 **/
@XmlRootElement(name = "Service")
public class ServiceInfo {
    private String name;
    private String status;
    private int proxyPoint;
    private ServiceHostsInfo hosts;

    public String getName() {
        return name;
    }
    @XmlAttribute
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    @XmlAttribute
    public void setStatus(String status) {
        this.status = status;
    }

    public int getProxyPoint() {
        return proxyPoint;
    }
    @XmlAttribute
    public void setProxyPoint(int proxyPoint) {
        this.proxyPoint = proxyPoint;
    }

    public ServiceHostsInfo getHosts() {
        return hosts;
    }
    @XmlElement(name = "Servers")
    public void setHosts(ServiceHostsInfo hosts) {
        this.hosts = hosts;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", proxyPoint=" + proxyPoint +
                ", hosts=" + hosts +
                '}';
    }
}
