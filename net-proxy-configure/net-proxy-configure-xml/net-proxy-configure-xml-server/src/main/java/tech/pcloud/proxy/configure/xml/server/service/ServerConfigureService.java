package tech.pcloud.proxy.configure.xml.server.service;

import tech.pcloud.proxy.configure.model.Server;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.core.utils.XmlHelper;
import tech.pcloud.proxy.configure.xml.server.exceptions.ServerXmlConfigureReadException;
import tech.pcloud.proxy.configure.xml.server.exceptions.ServerXmlConfigureSaveException;
import tech.pcloud.proxy.configure.xml.server.model.ServerInfo;
import tech.pcloud.proxy.configure.xml.server.service.agents.XmlServerInfoModelAgent;
import tech.pcloud.proxy.store.core.service.StoreService;

/**
 * @ClassName ServiceConfigureService
 * @Author pandong
 * @Date 2019/1/29 14:40
 **/
public class ServerConfigureService implements ConfigureService<Server> {
    private XmlServerInfoModelAgent xmlServerInfoModelAgent = new XmlServerInfoModelAgent();
    private StoreService<String> storeService;

    public ServerConfigureService(StoreService<String> storeService) {
        this.storeService = storeService;
    }

    @Override
    public void saveConfigure(Server config) {
        ServerInfo serverInfo = xmlServerInfoModelAgent.toSource(config);
        try {
            String content = XmlHelper.object2XmlString(serverInfo);
            storeService.save(content);
        } catch (Exception e) {
            throw new ServerXmlConfigureSaveException("save server config fail, " + e.getMessage(), e);
        }
    }

    @Override
    public Server loadConfigure() {
        String content = storeService.load();
        ServerInfo serverInfo = null;
        try {
            serverInfo = XmlHelper.xmlString2Object(content, ServerInfo.class);
        } catch (Exception e) {
            throw new ServerXmlConfigureReadException("load server config fail, " + e.getMessage(), e);
        }
        if (serverInfo == null) {
            throw new ServerXmlConfigureReadException("load server config fail, no client configure.");
        }
        return xmlServerInfoModelAgent.toTarget(serverInfo);
    }
}
