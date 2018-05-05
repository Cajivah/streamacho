package com.streamcho.chat;

import com.streamcho.chat.config.properties.ChatEndpointsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ChatEndpointsProperties.class)
public class ChatApplication {

     public static void main(String[] args) {
          SpringApplication.run(ChatApplication.class, args);
     }
}
