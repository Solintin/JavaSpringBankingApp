package org.bankapp.bankingapp.utils;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileUploader {
    private final Cloudinary cloudinary;

    public FileUploader(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public  String upload(String userEmail, MultipartFile multipartFile) throws IOException {
        Map<String, String> options = new HashMap<>();
        options.put("folder", "java_bankapp_profile_pictures");
        options.put("public_id", userEmail+"_profile_pictures");
        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
        return uploadResult.get("url").toString();
    }
}
