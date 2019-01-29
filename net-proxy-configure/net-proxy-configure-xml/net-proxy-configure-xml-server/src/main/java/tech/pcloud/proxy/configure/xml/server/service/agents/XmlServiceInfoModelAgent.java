package tech.pcloud.proxy.configure.xml.server.service.agents;

import tech.pcloud.proxy.configure.model.Service;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.server.model.ServiceInfo;

/**
 * @ClassName ServiceInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 15:17
 **/
public class XmlServiceInfoModelAgent implements ConfigureModelAgent<Service, ServiceInfo> {
    @Override
    public Service exchage2Target(ServiceInfo serviceInfo) {
        Service service = new Service();
        service.setName(serviceInfo.getName());
        service.setStatus(serviceInfo.getStatus());
        service.setProxyPort(serviceInfo.getProxyPort());
        return service;
    }

    @Override
    public ServiceInfo exchange2Source(Service service) {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setName(service.getName());
        serviceInfo.setProxyPort(service.getProxyPort());
        serviceInfo.setStatus(service.getStatus());
        return serviceInfo;
    }
}
