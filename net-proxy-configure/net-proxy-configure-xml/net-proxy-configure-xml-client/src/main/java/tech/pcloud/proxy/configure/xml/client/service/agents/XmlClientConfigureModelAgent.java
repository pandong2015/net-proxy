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
    private XmlServersInfoModelAgent xmlServersInfoModelAgent = new XmlServersInfoModelAgent();
    private XmlServicesInfoModelAgent xmlServicesInfoModelAgent = new XmlServicesInfoModelAgent();

    @Override
    public ClientConfig exchage2Target(ClientConfigure clientConfigure) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setPort(clientConfigure.getPort());
        clientConfig.setServices(xmlServicesInfoModelAgent.toTarget(clientConfigure.getServices()));
        clientConfig.setServers(xmlServersInfoModelAgent.toTarget(clientConfigure.getServers()));
        return clientConfig;
    }

    @Override
    public ClientConfigure exchange2Source(ClientConfig clientConfig) {
        ClientConfigure clientConfigure = new ClientConfigure();
        clientConfigure.setPort(clientConfig.getPort());
        clientConfigure.setServices(xmlServicesInfoModelAgent.toSource(clientConfig.getServices()));
        clientConfigure.setServers(xmlServersInfoModelAgent.toSource(clientConfig.getServers()));
        return clientConfigure;
    }
}
