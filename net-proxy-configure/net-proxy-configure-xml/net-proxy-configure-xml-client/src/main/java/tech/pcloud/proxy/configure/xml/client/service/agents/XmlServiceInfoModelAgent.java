package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.Service;
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
        service.setStatus(serviceInfo.getStatus());
        service.setTargets(xmlServiceHostsInfoModelAgent.toTarget(serviceInfo.getHosts()));
        return service;
    }

    @Override
    public ServiceInfo exchange2Source(Service service) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setStatus(service.getStatus());
        serviceInfo.setName(service.getName());
        serviceInfo.setProxyPoint(service.getProxyPort());
        serviceInfo.setHosts(xmlServiceHostsInfoModelAgent.exchange2Source(service.getTargets()));
        return serviceInfo;
    }
}
