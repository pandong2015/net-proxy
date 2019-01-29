package tech.pcloud.proxy.configure.xml.server.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @ClassName ServicesInfo
 * @Author pandong
 * @Date 2019/1/29 14:34
 **/
@XmlRootElement(name = "Services")
public class ServicesInfo {
    private List<ServiceInfo> services = Lists.newArrayList();

    public List<ServiceInfo> getServices() {
        return services;
    }

    @XmlElement(name = "Service")
    public void setServices(List<ServiceInfo> services) {
        this.services = services;
    }
}
