package tech.pcloud.proxy.configure.xml.server.service.agents;

import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.server.model.ServerInfo;

/**
 * @ClassName XmlServerInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 15:16
 **/
public class XmlServerInfoModelAgent implements ConfigureModelAgent<Server, ServerInfo> {
    private XmlClientsInfoModelAgent xmlClientsInfoModelAgent = new XmlClientsInfoModelAgent();

    @Override
    public Server exchage2Target(ServerInfo serverInfo) {
        Server server = new Server();
        server.setPort(serverInfo.getPort());
        server.setClients(xmlClientsInfoModelAgent.toTarget(serverInfo.getClients()));
        return server;
    }

    @Override
    public ServerInfo exchange2Source(Server server) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setPort(server.getPort());
        serverInfo.setClients(xmlClientsInfoModelAgent.toSource(server.getClients()));
        return serverInfo;
    }
}
