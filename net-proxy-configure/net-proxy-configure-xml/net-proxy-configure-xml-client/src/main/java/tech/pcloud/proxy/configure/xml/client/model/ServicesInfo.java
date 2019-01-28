package tech.pcloud.proxy.configure.xml.client.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @ClassName ServiceInfo
 * @Author pandong
 * @Date 2019/1/28 16:24
 **/
@XmlRootElement(name = "Services")
public class ServicesInfo {
    private List<ServiceInfo> services = Lists.newArrayList();

    public void addService(ServiceInfo service) {
        services.add(service);
    }

    public List<ServiceInfo> getServices() {
        return services;
    }

    @XmlElement(name = "Service")
    public void setServices(List<ServiceInfo> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "ServicesInfo{" +
                "services=" + services +
                '}';
    }
}
