package tech.pcloud.proxy.network.client;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ModelFactory;
import tech.pcloud.proxy.network.core.NetworkModel;

/**
 * @ClassName NetworkClientModel
 * @Author pandong
 * @Date 2019/1/29 15:53
 **/
public class NetworkClientModel extends NetworkModel {
    public enum NetworkClientModelFactory implements ModelFactory {
        INSTANCE;

        private Model model = new NetworkClientModel();

        @Override
        public Model getModel() {
            return model;
        }
    }
    public NetworkClientModel() {
        super(NetworkType.CLIENT);
    }
}
