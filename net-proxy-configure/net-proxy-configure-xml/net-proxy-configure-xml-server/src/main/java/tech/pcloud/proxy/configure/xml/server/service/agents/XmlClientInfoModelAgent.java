package tech.pcloud.proxy.configure.xml.server.service.agents;

import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.server.model.ClientInfo;

/**
 * @ClassName XmlClientInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 15:25
 **/
public class XmlClientInfoModelAgent implements ConfigureModelAgent<Client, ClientInfo> {
    private XmlServicesInfoModelAgent xmlServicesInfoModelAgent = new XmlServicesInfoModelAgent();

    @Override
    public Client exchage2Target(ClientInfo clientInfo) {
        Client client = new Client();
        client.setHost(clientInfo.getHost());
        client.setPort(clientInfo.getPort());
        client.setServices(xmlServicesInfoModelAgent.toTarget(clientInfo.getServices()));
        return client;
    }

    @Override
    public ClientInfo exchange2Source(Client client) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setHost(client.getHost());
        clientInfo.setPort(client.getPort());
        clientInfo.setServices(xmlServicesInfoModelAgent.toSource(client.getServices()));
        return clientInfo;
    }
}
