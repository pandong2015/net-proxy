package tech.pcloud.proxy.network.server;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ModelFactory;
import tech.pcloud.proxy.network.core.NetworkModel;

/**
 * @ClassName NetworkServerModel
 * @Author pandong
 * @Date 2019/1/29 15:54
 **/
public class NetworkServerModel extends NetworkModel {
    public enum NetworkServerModelFactory implements ModelFactory {
        INSTANCE;

        private Model model = new NetworkServerModel();

        @Override
        public Model getModel() {
            return model;
        }
    }

    public NetworkServerModel() {
        super(NetworkType.SERVER);
    }
}
