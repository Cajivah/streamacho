package com.streamcho.chatdb.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JacksonConfig {

     private ObjectMapper objectMapper;

     @Bean
     public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
          MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
          jsonConverter.setObjectMapper(objectMapper);
          return jsonConverter;
     }
}
