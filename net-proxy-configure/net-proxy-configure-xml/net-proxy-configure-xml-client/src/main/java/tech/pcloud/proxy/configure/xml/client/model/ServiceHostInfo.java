package tech.pcloud.proxy.configure.xml.client.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName ServiceHostInfo
 * @Author pandong
 * @Date 2019/1/28 16:26
 **/
@XmlRootElement(name = "Ref")
public class ServiceHostInfo {
    private String name;
    private String status;

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

    @Override
    public String toString() {
        return "ServiceHostInfo{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
