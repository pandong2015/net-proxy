package tech.pcloud.proxy.configure.xml.client.service;

import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.configure.model.ClientConfig;
import tech.pcloud.proxy.configure.service.ConfigureService;
import tech.pcloud.proxy.configure.xml.client.exceptions.ClientXmlConfigureReadException;
import tech.pcloud.proxy.configure.xml.client.exceptions.ClientXmlConfigureSaveException;
import tech.pcloud.proxy.configure.xml.client.model.ClientConfigure;
import tech.pcloud.proxy.configure.xml.client.service.agents.XmlClientConfigureModelAgent;
import tech.pcloud.proxy.configure.xml.core.utils.XmlHelper;
import tech.pcloud.proxy.store.core.service.StoreService;

/**
 * @ClassName ClientConfigureService
 * @Author pandong
 * @Date 2019/1/29 10:10
 **/
@Slf4j
public class ClientConfigureService implements ConfigureService<ClientConfig> {
    private StoreService<String> storeService;
    private XmlClientConfigureModelAgent xmlClientConfigureModelAgent = new XmlClientConfigureModelAgent();

    public ClientConfigureService(StoreService<String> storeService) {
        this.storeService = storeService;
    }

    @Override
    public void saveConfigure(ClientConfig config) {
        ClientConfigure clientConfigure = xmlClientConfigureModelAgent.exchange2Source(config);
        try {
            String content = XmlHelper.object2XmlString(clientConfigure);
            log.debug("save content: {}", content);
            storeService.save(content);
            log.info(("save config success."));
        } catch (Exception e) {
            throw new ClientXmlConfigureSaveException("save client config fail, " + e.getMessage(), e);
        }
    }

    @Override
    public ClientConfig loadConfigure() {
        String content = storeService.load();
        ClientConfigure clientConfigure = null;
        try {
            log.debug("load configure content: {}", content);
            clientConfigure = XmlHelper.xmlString2Object(content, ClientConfigure.class);
            log.info("load configure success, configure type: {}", ClientConfigure.class.getName());
        } catch (Exception e) {
            throw new ClientXmlConfigureReadException("load client config fail, " + e.getMessage(), e);
        }
        if (clientConfigure == null) {
            throw new ClientXmlConfigureReadException("load client config fail, no client configure.");
        }

        return xmlClientConfigureModelAgent.toTarget(clientConfigure);
    }
}
