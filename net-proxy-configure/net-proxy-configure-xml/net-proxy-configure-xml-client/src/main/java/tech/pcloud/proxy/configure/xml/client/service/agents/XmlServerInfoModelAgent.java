package tech.pcloud.proxy.configure.xml.client.service.agents;

import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.service.agents.ConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.client.model.ServerInfo;

/**
 * @ClassName XmlServerInfoModelAgent
 * @Author pandong
 * @Date 2019/1/29 11:39
 **/
public class XmlServerInfoModelAgent implements ConfigureModelAgent<Server, ServerInfo> {
    @Override
    public Server exchage2Target(ServerInfo serverInfo) {
        Server server = new Server();
        server.setName(serverInfo.getName());
        server.setHost(serverInfo.getHost());
        server.setPort(serverInfo.getPort());
        return server;
    }

    @Override
    public ServerInfo exchange2Source(Server server) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setName(server.getName());
        serverInfo.setPort(server.getPort());
        serverInfo.setHost(server.getHost());
        return serverInfo;
    }
}
