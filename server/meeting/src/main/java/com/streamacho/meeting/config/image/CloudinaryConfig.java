package com.streamacho.meeting.config.image;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.streamacho.meeting.config.properties.CloudinaryProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({CloudinaryProperties.class})
public class CloudinaryConfig {

     private final CloudinaryProperties cloudinaryProperties;

     @Bean
     public Cloudinary cloudinary() {
          return new Cloudinary(ObjectUtils.asMap(
                  "cloud_name", cloudinaryProperties.getCloudName(),
                  "api_key", cloudinaryProperties.getApiKey(),
                  "api_secret", cloudinaryProperties.getApiSecret()
          ));
     }
}
