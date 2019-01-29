package tech.pcloud.proxy.configure.xml.client;

import tech.pcloud.proxy.configure.xml.core.XmlConfigureModel;
import tech.pcloud.proxy.core.Model;

/**
 * @ClassName ClientXmlConfigureModel
 * @Author pandong
 * @Date 2019/1/28 15:53
 **/
public class ClientXmlConfigureModel extends XmlConfigureModel {
    public enum ClientXmlConfigureModelFactory {
        INSTANCE;
        private Model model = new ClientXmlConfigureModel();

        public Model getModel() {
            return model;
        }
    }

    public ClientXmlConfigureModel() {
        super(ConfigureType.CLIENT);
    }
}
