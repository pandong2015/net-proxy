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
    private ServiceDeployInfo deploy;

    public String getName() {
        return name;
    }
    @XmlAttribute(name = "name")
    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }
    @XmlAttribute(name = "status")
    public void setStatus(String status) {
        this.status = status;
    }

    public int getProxyPoint() {
        return proxyPoint;
    }
    @XmlAttribute(name = "proxyPort")
    public void setProxyPoint(int proxyPoint) {
        this.proxyPoint = proxyPoint;
    }

    public ServiceDeployInfo getDeploy() {
        return deploy;
    }
    @XmlElement(name = "Deploy")
    public void setDeploy(ServiceDeployInfo deploy) {
        this.deploy = deploy;
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", proxyPoint=" + proxyPoint +
                ", deploy=" + deploy +
                '}';
    }
}
