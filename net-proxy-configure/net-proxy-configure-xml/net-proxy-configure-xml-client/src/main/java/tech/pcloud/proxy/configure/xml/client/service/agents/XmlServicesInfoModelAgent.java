package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServicesInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName XmlServicesInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:41
 **/
public class XmlServicesInfoModelAgent implements ConfigureModelAgent<List<Service>, ServicesInfo> {
    private XmlServiceInfoModelAgent xmlServiceInfoModelAgent = new XmlServiceInfoModelAgent();

    @Override
    public List<Service> exchage2Target(ServicesInfo servicesInfo) {
        return servicesInfo.getServices().stream().map(s -> xmlServiceInfoModelAgent.toTarget(s)).collect(Collectors.toList());
    }

    @Override
    public ServicesInfo exchange2Source(List<Service> services) {
        ServicesInfo servicesInfo = new ServicesInfo();
        servicesInfo.setServices(services.stream()
                .map(s -> xmlServiceInfoModelAgent.exchange2Source(s))
                .collect(Collectors.toList()));
        return servicesInfo;
    }
}
