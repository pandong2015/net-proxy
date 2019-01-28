package tech.pcloud.proxy.store.filesystem.utils;

import tech.pcloud.proxy.core.Model;
import tech.pcloud.proxy.core.ProxyException;
import tech.pcloud.proxy.store.filesystem.exceptions.FileSystemStoreException;
import tech.pcloud.proxy.store.filesystem.exceptions.FileSystemStoreReadException;
import tech.pcloud.proxy.store.filesystem.exceptions.FileSystemStoreSaveException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @ClassName FileHelper
 * @Author pandong
 * @Date 2019/1/28 10:38
 **/
public class FileHelper {
    public static void save(Path file, String content) {
        save(file, content.getBytes(Charset.forName(Model.DEFAULT_ENCODING)));
    }

    public static void save(Path file, byte[] content) {
        try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(file))) {
            out.write(content);
        } catch (IOException e) {
            throw new FileSystemStoreSaveException("save file[" + file.toString() + "] fail, " + e.getMessage(), e);
        }
    }

    public static String readString(Path path) {
        return new String(readBytes(path), Charset.forName(Model.DEFAULT_ENCODING));
    }

    public static byte[] readBytes(Path file) {
        byte[] content = null;
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(file));
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] data = new byte[Model.BYTE_BUFFERED_SIZE];
            int len = -1;
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
            content = out.toByteArray();
        } catch (IOException e) {
            throw new FileSystemStoreReadException("read file[" + file.toString() + "] fail, " + e.getMessage(), e);
        }
        return content;
    }
}
