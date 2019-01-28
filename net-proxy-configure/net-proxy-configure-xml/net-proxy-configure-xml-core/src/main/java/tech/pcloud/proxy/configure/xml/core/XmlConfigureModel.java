package tech.pcloud.proxy.configure.xml.core;

import tech.pcloud.proxy.configure.ConfigureModel;

/**
 * @ClassName XmlConfigureModel
 * @Author pandong
 * @Date 2019/1/28 15:26
 **/
public class XmlConfigureModel extends ConfigureModel {

    public XmlConfigureModel(ConfigureType type) {
        super(type, ConfigureStoreType.XML);
    }
}
