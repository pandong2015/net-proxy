package tech.pcloud.proxy.store.filesystem;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ModelFactory;
import tech.pcloud.proxy.store.core.StoreModel;

/**
 * @ClassName FileSystemStoreModel
 * @Author pandong
 * @Date 2019/1/28 10:59
 **/
public class FileSystemStoreModel extends StoreModel {

    public enum FileSystemStoreModelFactory implements ModelFactory {
        INSTANCE;

        private Model model = new FileSystemStoreModel();

        @Override
        public Model getModel() {
            return model;
        }
    }

    public FileSystemStoreModel() {
        super(StoreType.FileSystem);
    }
}
