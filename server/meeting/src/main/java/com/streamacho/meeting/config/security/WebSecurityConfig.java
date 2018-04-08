package com.streamacho.meeting.config.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static com.streamacho.meeting.config.security.util.SecurityConsts.ANY_PATH;
import static com.streamacho.meeting.config.security.util.SecurityConsts.AUTH_WHITELIST;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     // @formatter:off
     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .csrf().disable()
               .cors()
               .and()
                    .authorizeRequests()
                         .antMatchers(AUTH_WHITELIST).permitAll()
                         .antMatchers(HttpMethod.OPTIONS, ANY_PATH).permitAll()
                         .anyRequest().authenticated()
               .and()
                    .sessionManagement()
                         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          ;
     }
     // @formatter:on
}
