package tech.pcloud.proxy.configure;

import tech.pcloud.proxy.core.Model;

/**
 * @ClassName ConfigureModel
 * @Author pandong
 * @Date 2019/1/28 15:03
 **/
public class ConfigureModel implements Model {
    public enum ConfigureType {
        SERVER, CLIENT;
    }

    public enum ConfigureStoreType {
        XML, DATABASE
    }

    private ConfigureType type;
    private ConfigureStoreType storeType;

    public ConfigureModel(ConfigureType type, ConfigureStoreType storeType) {
        this.type = type;
        this.storeType = storeType;
    }

    @Override
    public int getModelCode() {
        return 20000 + storeType.ordinal() * 1000 + type.ordinal() * 100;
    }
}
