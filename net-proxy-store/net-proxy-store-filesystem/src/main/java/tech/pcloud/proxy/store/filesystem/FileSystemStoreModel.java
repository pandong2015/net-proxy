package tech.pcloud.proxy.store.filesystem;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.store.core.StoreModel;

/**
 * @ClassName FileSystemStoreModel
 * @Author pandong
 * @Date 2019/1/28 10:59
 **/
public class FileSystemStoreModel extends StoreModel {

    public enum FileSystemStoreModelFactory{
        INSTANCE;

        private Model model = new FileSystemStoreModel();

        public Model getModel() {
            return model;
        }
    }

    public FileSystemStoreModel() {
        super(StoreType.FileSystem);
    }
}
