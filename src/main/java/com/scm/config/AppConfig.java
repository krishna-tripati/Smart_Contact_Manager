package com.scm.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfig {
    // cloud information getting from application properties
    @Value("${cloudinary.cloud.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apikey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;

    // bean for cloudinary use anywhere
    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(
                ObjectUtils.asMap(
                 "cloud_name",cloudName,
                        "api_key",apikey,
                        "api_secret",apiSecret
                )
        );

    }
}
