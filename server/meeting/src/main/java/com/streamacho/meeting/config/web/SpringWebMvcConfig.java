package com.streamacho.meeting.config.web;

import com.streamacho.meeting.config.converter.StringToEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringWebMvcConfig implements WebMvcConfigurer {

     @Override
     public void addFormatters(FormatterRegistry registry) {
          registry.addConverterFactory(new StringToEnumConverterFactory());
     }
}
