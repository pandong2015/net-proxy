package tech.pcloud.proxy.store.filesystem.service;

import lombok.extern.slf4j.Slf4j;
import tech.pcloud.proxy.store.core.service.StoreService;
import tech.pcloud.proxy.store.filesystem.utils.FileHelper;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @ClassName FileSystemStoreService
 * @Author pandong
 * @Date 2019/1/28 15:19
 **/
@Slf4j
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
        log.info("save file : {}", filePath);
        FileHelper.save(filePath, s);
    }

    @Override
    public String load() {
        log.info("load file : {}", filePath);
        return FileHelper.readString(filePath);
    }
}
