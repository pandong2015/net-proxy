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
    private XmlServerInfoModelAgent xmlServerInfoModelAgent = new XmlServerInfoModelAgent();
    private XmlServicesInfoModelAgent xmlServicesInfoModelAgent = new XmlServicesInfoModelAgent();

    @Override
    public ClientConfig exchage2Target(ClientConfigure clientConfigure) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setId(clientConfigure.getId());
        clientConfig.setPort(clientConfigure.getPort());
        clientConfig.setServices(xmlServicesInfoModelAgent.toTarget(clientConfigure.getServices()));
        clientConfig.setServer(xmlServerInfoModelAgent.toTarget(clientConfigure.getServer()));
        return clientConfig;
    }

    @Override
    public ClientConfigure exchange2Source(ClientConfig clientConfig) {
        ClientConfigure clientConfigure = new ClientConfigure();
        clientConfigure.setId(clientConfig.getId());
        clientConfigure.setPort(clientConfig.getPort());
        clientConfigure.setServices(xmlServicesInfoModelAgent.toSource(clientConfig.getServices()));
        clientConfigure.setServer(xmlServerInfoModelAgent.toSource(clientConfig.getServer()));
        return clientConfigure;
    }
}
