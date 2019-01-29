package tech.pcloud.proxy.configure.xml.server;

import tech.pcloud.proxy.configure.xml.core.XmlConfigureModel;
import tech.pcloud.proxy.core.Model;

/**
 * @ClassName ClientXmlConfigureModel
 * @Author pandong
 * @Date 2019/1/28 15:53
 **/
public class ServerXmlConfigureModel extends XmlConfigureModel {
    public enum ServerXmlConfigureModelFactory {
        INSTANCE;
        private Model model = new ServerXmlConfigureModel();

        public Model getModel() {
            return model;
        }
    }

    public ServerXmlConfigureModel() {
        super(ConfigureType.SERVER);
    }
}
