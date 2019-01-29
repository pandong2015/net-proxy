package tech.pcloud.proxy.configure.xml.server;

import tech.pcloud.proxy.configure.xml.core.XmlConfigureModel;
import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ModelFactory;

/**
 * @ClassName ClientXmlConfigureModel
 * @Author pandong
 * @Date 2019/1/28 15:53
 **/
public class ServerXmlConfigureModel extends XmlConfigureModel {
    public enum ServerXmlConfigureModelFactory implements ModelFactory {
        INSTANCE;
        private Model model = new ServerXmlConfigureModel();

        @Override
        public Model getModel() {
            return model;
        }
    }

    public ServerXmlConfigureModel() {
        super(ConfigureType.SERVER);
    }
}
