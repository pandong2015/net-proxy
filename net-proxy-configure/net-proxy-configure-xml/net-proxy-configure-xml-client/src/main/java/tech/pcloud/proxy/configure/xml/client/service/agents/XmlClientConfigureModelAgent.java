package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.ClientConfig;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ClientConfigure;

/**
 * @ClassName ClientXmlConfigureModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:01
 **/
public class XmlClientConfigureModelAgent implements ConfigureModelAgent<ClientConfig, ClientConfigure> {
    private XmlClientInfoModelAgent xmlClientInfoModelAgent = new XmlClientInfoModelAgent();
    private XmlServersInfoModelAgent xmlServersInfoModelAgent = new XmlServersInfoModelAgent();

    @Override
    public ClientConfig exchage2Target(ClientConfigure clientConfigure) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setClient(xmlClientInfoModelAgent.toTarget(clientConfigure.getClient()));
        clientConfig.setServers(xmlServersInfoModelAgent.toTarget(clientConfigure.getServers()));
        return clientConfig;
    }

    @Override
    public ClientConfigure exchange2Source(ClientConfig clientConfig) {
        ClientConfigure clientConfigure = new ClientConfigure();
        clientConfigure.setClient(xmlClientInfoModelAgent.exchange2Source(clientConfig.getClient()));
        clientConfigure.setServers(xmlServersInfoModelAgent.exchange2Source(clientConfig.getServers()));
        return clientConfigure;
    }
}
