package tech.pcloud.proxy.configure.xml.client.model;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @ClassName ServiceHostInfo
 * @Author pandong
 * @Date 2019/1/28 16:26
 **/
@XmlRootElement(name = "Deploy")
public class ServiceDeployInfo {
    private List<ServiceDeployRefInfo> refs = Lists.newArrayList();

    public void addHost(ServiceDeployRefInfo host){
        refs.add(host);
    }

    public List<ServiceDeployRefInfo> getRefs() {
        return refs;
    }

    @XmlElement(name = "Ref")
    public void setRefs(List<ServiceDeployRefInfo> refs) {
        this.refs = refs;
    }

    @Override
    public String toString() {
        return "ServiceHostsInfo{" +
                "refs=" + refs +
                '}';
    }
}
