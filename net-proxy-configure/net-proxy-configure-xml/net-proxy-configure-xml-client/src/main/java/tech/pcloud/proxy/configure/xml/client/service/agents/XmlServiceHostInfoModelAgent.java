package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.ProxyTarget;
import tech.pcloud.proxy.configure.model.Status;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServiceDeployRefInfo;

/**
 * @ClassName XmlServiceHostInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:42
 **/
public class XmlServiceHostInfoModelAgent implements ConfigureModelAgent<ProxyTarget, ServiceDeployRefInfo> {
    @Override
    public ProxyTarget exchage2Target(ServiceDeployRefInfo serviceDeployRefInfo) {
        ProxyTarget proxyTarget = new ProxyTarget();
        proxyTarget.setStatus(Status.valueOf(serviceDeployRefInfo.getStatus()));
        proxyTarget.setServerName(serviceDeployRefInfo.getName());
        return proxyTarget;
    }

    @Override
    public ServiceDeployRefInfo exchange2Source(ProxyTarget proxyTarget) {
        ServiceDeployRefInfo serviceDeployRefInfo = new ServiceDeployRefInfo();
        serviceDeployRefInfo.setStatus(proxyTarget.getStatus().name());
        serviceDeployRefInfo.setName(proxyTarget.getServerName());
        return serviceDeployRefInfo;
    }
}
