package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServersInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName XmlServersInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:53
 **/
public class XmlServersInfoModelAgent implements ConfigureModelAgent<List<Server>, ServersInfo> {
    private XmlServerInfoModelAgent xmlServerInfoModelAgent = new XmlServerInfoModelAgent();

    @Override
    public List<Server> exchage2Target(ServersInfo serversInfo) {
        List<Server> servers = serversInfo.getServers().stream()
                .map(s -> xmlServerInfoModelAgent.toTarget(s))
                .collect(Collectors.toList());
        return servers;
    }

    @Override
    public ServersInfo exchange2Source(List<Server> servers) {
        ServersInfo serversInfo = new ServersInfo();
        servers.stream().map(s -> xmlServerInfoModelAgent.toSource(s))
                .forEach(s -> serversInfo.addServer(s));
        return serversInfo;
    }
}
