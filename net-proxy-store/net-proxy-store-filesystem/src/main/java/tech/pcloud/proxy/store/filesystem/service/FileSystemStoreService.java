package tech.pcloud.proxy.store.filesystem.service;

import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.utils.FileHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName FileSystemStoreService
 * @Author pandong
 * @Date 2019/1/28 15:19
 **/
public class FileSystemStoreService implements StoreService<String> {
    private Path filePath;

    public FileSystemStoreService(Path filePath) {
        this.filePath = filePath;
    }

    public FileSystemStoreService(String filePath, String fileName) {
        this.filePath = Paths.get(filePath, fileName);
    }

    @Override
    public void save(String s) {
        FileHelper.save(filePath, s);
    }

    @Override
    public String load() {
        return FileHelper.readString(filePath);
    }
}
