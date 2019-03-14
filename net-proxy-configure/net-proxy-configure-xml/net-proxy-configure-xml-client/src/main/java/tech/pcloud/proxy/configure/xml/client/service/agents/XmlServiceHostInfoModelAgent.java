package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.ProxyTarget;
import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.model.Status;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServiceHostInfo;

/**
 * @ClassName XmlServiceHostInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:42
 **/
public class XmlServiceHostInfoModelAgent implements ConfigureModelAgent<ProxyTarget, ServiceHostInfo> {
    @Override
    public ProxyTarget exchage2Target(ServiceHostInfo serviceHostInfo) {
        ProxyTarget proxyTarget = new ProxyTarget();
        proxyTarget.setStatus(Status.valueOf(serviceHostInfo.getStatus()));
        proxyTarget.setServerName(serviceHostInfo.getName());
        return proxyTarget;
    }

    @Override
    public ServiceHostInfo exchange2Source(ProxyTarget proxyTarget) {
        ServiceHostInfo serviceHostInfo = new ServiceHostInfo();
        serviceHostInfo.setStatus(proxyTarget.getStatus().name());
        serviceHostInfo.setName(proxyTarget.getServerName());
        return serviceHostInfo;
    }
}
