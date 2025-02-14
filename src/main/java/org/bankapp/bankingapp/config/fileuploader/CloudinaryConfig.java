package org.bankapp.bankingapp.config.fileuploader;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String cloudApiKey;

    @Value("${cloudinary.secret-key}")
    private String cloudSecretKey;



    @Bean
    public Cloudinary cloudinary() {
        System.out.println(cloudApiKey);
        System.out.println(cloudName);
        System.out.println(cloudSecretKey);
        return new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", cloudName,
                        "api_key", cloudApiKey,
                        "api_secret", cloudSecretKey
                )
        );
    }
}
