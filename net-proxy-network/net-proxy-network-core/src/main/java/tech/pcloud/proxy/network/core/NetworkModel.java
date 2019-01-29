package tech.pcloud.proxy.network.core;

import tech.pcloud.proxy.core.Model;

/**
 * @ClassName NetworkModel
 * @Author pandong
 * @Date 2019/1/29 15:51
 **/
public class NetworkModel implements Model {
    public enum NetworkType {
        SERVER, CLIENT
    }

    private NetworkType type;

    public NetworkModel(NetworkType type) {
        this.type = type;
    }

    @Override
    public int getModelCode() {
        return 300 + type.ordinal() * 10;
    }
}
