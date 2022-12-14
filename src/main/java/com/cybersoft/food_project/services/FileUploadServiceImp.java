package com.cybersoft.food_project.services;

import com.cybersoft.food_project.model.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServiceImp implements FileUploadService {

    private Path rootPath;
    @Autowired
    public FileUploadServiceImp(FileStorageProperties fileStorageProperties) throws IOException {
        // Định nghĩa đường dẫn root
        this.rootPath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        if(Files.notExists(this.rootPath)){
            // Tạo folder lưu file nếu khng tồn tại folder
            Files.createDirectories(rootPath);
        }
    }

    @Override
    public boolean storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            //resolve giống như dấu "/"
            Files.copy(file.getInputStream(), rootPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            System.out.println("Loi save file" + e.getMessage());
            return false;
        }
    }

    @Override
    public Resource loadFileByName(String fileName) {
        try {
            Path path = this.rootPath.resolve(fileName).normalize();
            //byte[]
            //base64
            //resource
            Resource resource = new UrlResource(path.toUri());
            if(resource.exists()) {
                return resource;
            }
        } catch (Exception e) {
            System.out.println("Lỗi đọc file " + e.getMessage());
        }
            return null;
    }
}
