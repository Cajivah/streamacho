package com.streamacho.chat.config;

import com.streamacho.chat.config.properties.ChatEndpointsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

     private final ChatEndpointsProperties chatEndpointsProperties;

     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .authorizeRequests()
               .antMatchers(chatEndpointsProperties.getWebSocketEndpoint() + "/**").permitAll()
               .antMatchers("/chat/**").permitAll()
               .anyRequest().denyAll();
     }

}
