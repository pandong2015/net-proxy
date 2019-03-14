package tech.pcloud.proxy.configure.xml.client.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @ClassName ServiceHostInfo
 * @Author pandong
 * @Date 2019/1/28 16:26
 **/
@XmlRootElement(name = "Servers")
public class ServiceHostsInfo {
    private List<ServiceHostInfo> hosts = Lists.newArrayList();

    public void addHost(ServiceHostInfo host){
        hosts.add(host);
    }
    public List<ServiceHostInfo> getHosts() {
        return hosts;
    }
    @XmlElement(name = "Servers")
    public void setHosts(List<ServiceHostInfo> hosts) {
        this.hosts = hosts;
    }

    @Override
    public String toString() {
        return "ServiceHostsInfo{" +
                "hosts=" + hosts +
                '}';
    }
}
