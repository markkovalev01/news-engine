package app.utils;

import lombok.SneakyThrows;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {
    @Autowired
    Path root;

    @SneakyThrows
    public void save(MultipartFile file, String name) {
        try (InputStream in = file.getInputStream()) {
            try (FileOutputStream out = new FileOutputStream(this.root.resolve(name).toFile())) {
                FileCopyUtils.copy(in,out);
            }
        }
    }

    public Path load(String filename) {
        return root.resolve(filename);
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path file = Paths.get(root.toUri())
                .resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException(String.format("Could not read the file! Path:%s", file));
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @SneakyThrows
    public byte[] loadFileAsByteArray(String filename) {
        return FileUtils.readFileToByteArray(load(filename).toFile());
    }

    @SneakyThrows
    public boolean compareFiles(MultipartFile newFile, String oldFileName) {
        return IOUtils.contentEquals(newFile.getInputStream(), Files.newInputStream(load(oldFileName)));
    }

    public String getExtension(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }

}
