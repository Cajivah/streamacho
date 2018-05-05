package com.streamcho.chat.config;

import com.streamcho.chat.config.properties.ChatEndpointsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

     private final ChatEndpointsProperties chatEndpointsProperties;

     @Autowired
     public WebSecurityConfiguration(ChatEndpointsProperties chatEndpointsProperties) {
          this.chatEndpointsProperties = chatEndpointsProperties;
     }


     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .authorizeRequests()
               .antMatchers(chatEndpointsProperties.getWebSocketEndpoint() + "/**").permitAll()
               .antMatchers("/chat/**").permitAll()
               .anyRequest().denyAll();
     }

}
