package tech.pcloud.proxy.configure.xml.server.service.agents;

import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.server.model.ServicesInfo;
import tech.pcloud.proxy.configure.model.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ServicesInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 15:19
 **/
public class XmlServicesInfoModelAgent implements ConfigureModelAgent<List<Service>, ServicesInfo> {
    private XmlServiceInfoModelAgent serviceInfoModelAgent = new XmlServiceInfoModelAgent();

    @Override
    public List<Service> exchage2Target(ServicesInfo servicesInfo) {
        return servicesInfo.getServices().stream()
                .map(s -> serviceInfoModelAgent.toTarget(s))
                .collect(Collectors.toList());
    }

    @Override
    public ServicesInfo exchange2Source(List<Service> services) {
        ServicesInfo servicesInfo = new ServicesInfo();
        servicesInfo.setServices(services.stream()
                .map(s -> serviceInfoModelAgent.toSource(s))
                .collect(Collectors.toList()));
        return servicesInfo;
    }
}
