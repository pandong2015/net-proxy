package tech.pcloud.proxy.store.core;

import tech.pcloud.proxy.core.Model;

/**
 * @ClassName StoreModel
 * @Author pandong
 * @Date 2019/1/28 11:47
 **/
public class StoreModel implements Model {
    public enum StoreType{
        FileSystem, Database
    }

    private StoreType type;

    public StoreModel(StoreType type) {
        this.type = type;
    }

    @Override
    public int getModelCode() {
        return 10000+type.ordinal()*1000;
    }
}
