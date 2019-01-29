package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.Client;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ClientInfo;

/**
 * @ClassName XmlClientInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:05
 **/
public class XmlClientInfoModelAgent implements ConfigureModelAgent<Client, ClientInfo> {
    private XmlServicesInfoModelAgent xmlServicesInfoModelAgent = new XmlServicesInfoModelAgent();

    @Override
    public Client exchage2Target(ClientInfo clientInfo) {
        Client client = new Client();
        client.setPort(clientInfo.getPort());
        client.setServices(xmlServicesInfoModelAgent.toTarget(clientInfo.getServices()));
        return client;
    }

    @Override
    public ClientInfo exchange2Source(Client client) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setPort(client.getPort());
        clientInfo.setServices(xmlServicesInfoModelAgent.exchange2Source(client.getServices()));
        return clientInfo;
    }
}
