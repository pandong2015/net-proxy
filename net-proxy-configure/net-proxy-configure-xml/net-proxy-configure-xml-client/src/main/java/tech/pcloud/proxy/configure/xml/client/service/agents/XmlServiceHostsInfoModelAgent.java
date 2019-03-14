package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.ProxyTarget;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServiceHostsInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName XmlServiceHostsInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:47
 **/
public class XmlServiceHostsInfoModelAgent implements ConfigureModelAgent<List<ProxyTarget>, ServiceHostsInfo> {
    private XmlServiceHostInfoModelAgent xmlServiceHostInfoModelAgent = new XmlServiceHostInfoModelAgent();

    @Override
    public List<ProxyTarget> exchage2Target(ServiceHostsInfo serviceHostsInfo) {
        return serviceHostsInfo.getHosts().stream()
                .map(h -> xmlServiceHostInfoModelAgent.toTarget(h))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceHostsInfo exchange2Source(List<ProxyTarget> proxyTargets) {
        ServiceHostsInfo serviceHostsInfo = new ServiceHostsInfo();
        serviceHostsInfo.setHosts(proxyTargets.stream()
                .map(p -> xmlServiceHostInfoModelAgent.toSource(p))
                .collect(Collectors.toList()));
        return serviceHostsInfo;
    }
}
