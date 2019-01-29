package tech.pcloud.proxy.configure.xml.server.service.agents;

import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.server.model.ClientsInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName XmlClientsInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 15:27
 **/
public class XmlClientsInfoModelAgent implements ConfigureModelAgent<List<Client>, ClientsInfo> {
    private XmlClientInfoModelAgent xmlClientInfoModelAgent = new XmlClientInfoModelAgent();

    @Override
    public List<Client> exchage2Target(ClientsInfo clientsInfo) {
        return clientsInfo.getClients().stream()
                .map(c -> xmlClientInfoModelAgent.toTarget(c))
                .collect(Collectors.toList());
    }

    @Override
    public ClientsInfo exchange2Source(List<Client> clients) {
        ClientsInfo clientsInfo = new ClientsInfo();
        clientsInfo.setClients(clients.stream().map(c -> xmlClientInfoModelAgent.toSource(c))
                .collect(Collectors.toList()));
        return clientsInfo;
    }
}
