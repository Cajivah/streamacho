package com.streamacho.meeting.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties("cloudinary")
public class CloudinaryProperties {

     @NotBlank
     private String cloudName;

     @NotBlank
     private String apiKey;

     @NotBlank
     private String apiSecret;
}