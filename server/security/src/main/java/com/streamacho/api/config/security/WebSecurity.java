package com.streamacho.api.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

     private static final String[] AUTH_WHITELIST = {
          // -- swagger ui
          "/v2/api-docs",
          "/swagger-resources",
          "/swagger-resources/**",
          "/configuration/ui",
          "/configuration/security",
          "/swagger-ui.html",
          "/webjars/**"
     };

     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .authorizeRequests()
               .antMatchers(AUTH_WHITELIST).permitAll()
               .antMatchers("/**").authenticated();
     }
}
