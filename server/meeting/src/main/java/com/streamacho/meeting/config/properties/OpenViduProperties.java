package com.streamacho.meeting.config.properties;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties("openvidu")
public class OpenViduProperties {

     @NotBlank
     private String secret;

     @NotBlank
     private String url;
}
