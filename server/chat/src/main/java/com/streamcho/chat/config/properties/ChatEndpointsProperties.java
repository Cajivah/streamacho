package com.streamcho.chat.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
@ConfigurationProperties("streamcho.chat")
public class ChatEndpointsProperties {

     @NotBlank
     private String chatSubscribePrefix;

     @NotBlank
     private String chatSendPrefix;

     @NotBlank
     private String webSocketEndpoint;

}
