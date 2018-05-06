package com.streamacho.chat.config;

import com.streamacho.chat.config.properties.ChatEndpointsProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@AllArgsConstructor(onConstructor = @__(@Autowired))
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
