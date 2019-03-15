package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.ProxyTarget;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServiceDeployInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName XmlServiceHostsInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:47
 **/
public class XmlServiceHostsInfoModelAgent implements ConfigureModelAgent<List<ProxyTarget>, ServiceDeployInfo> {
    private XmlServiceHostInfoModelAgent xmlServiceHostInfoModelAgent = new XmlServiceHostInfoModelAgent();

    @Override
    public List<ProxyTarget> exchage2Target(ServiceDeployInfo serviceDeployInfo) {
        return serviceDeployInfo.getRefs().stream()
                .map(h -> xmlServiceHostInfoModelAgent.toTarget(h))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceDeployInfo exchange2Source(List<ProxyTarget> proxyTargets) {
        ServiceDeployInfo serviceDeployInfo = new ServiceDeployInfo();
        serviceDeployInfo.setRefs(proxyTargets.stream()
                .map(p -> xmlServiceHostInfoModelAgent.toSource(p))
                .collect(Collectors.toList()));
        return serviceDeployInfo;
    }
}
