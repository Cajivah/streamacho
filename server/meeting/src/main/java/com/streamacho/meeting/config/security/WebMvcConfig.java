package com.streamacho.meeting.config.security;

import com.streamacho.meeting.config.properties.RoutingProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
@AllArgsConstructor(onConstructor = @__(@Autowired))
@EnableConfigurationProperties({RoutingProperties.class})
public class WebMvcConfig implements WebMvcConfigurer {

     private final MappingJackson2HttpMessageConverter converter;
     private final RoutingProperties routingProperties;

     @Override
     public void addCorsMappings(CorsRegistry registry) {
          registry.addMapping("/**")
               .allowedOrigins(routingProperties.getFrontendOrigin())
          ;
     }

     @Override
     public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
          converters.add(converter);
     }

     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {

          registry.addResourceHandler("swagger-ui.html")
               .addResourceLocations("classpath:/META-INF/resources/");

          registry.addResourceHandler("/webjars/**")
               .addResourceLocations("classpath:/META-INF/resources/webjars/");
     }
}
