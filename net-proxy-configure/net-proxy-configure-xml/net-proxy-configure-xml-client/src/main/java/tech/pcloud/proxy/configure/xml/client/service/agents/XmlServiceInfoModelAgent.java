package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.configure.model.Status;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServiceInfo;

/**
 * @ClassName XmlServiceInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:48
 **/
public class XmlServiceInfoModelAgent implements ConfigureModelAgent<Service, ServiceInfo> {
    private XmlServiceHostsInfoModelAgent xmlServiceHostsInfoModelAgent = new XmlServiceHostsInfoModelAgent();

    @Override
    public Service exchage2Target(ServiceInfo serviceInfo) {
        Service service = new Service();
        service.setName(serviceInfo.getName());
        service.setProxyPort(serviceInfo.getProxyPoint());
        service.setStatus(Status.valueOf(serviceInfo.getStatus()));
        service.setTargets(xmlServiceHostsInfoModelAgent.toTarget(serviceInfo.getDeploy()));
        return service;
    }

    @Override
    public ServiceInfo exchange2Source(Service service) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setStatus(service.getStatus().name());
        serviceInfo.setName(service.getName());
        serviceInfo.setProxyPoint(service.getProxyPort());
        serviceInfo.setDeploy(xmlServiceHostsInfoModelAgent.toSource(service.getTargets()));
        return serviceInfo;
    }
}
