package com.streamacho.meeting.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamacho.meeting.config.security.exception.FailureHandlingAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

import static com.streamacho.meeting.config.security.util.SecurityConsts.ANY_PATH;
import static com.streamacho.meeting.config.security.util.SecurityConsts.AUTH_WHITELIST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     private final ObjectMapper objectMapper;

     public AuthenticationEntryPoint authenticationEntryPoint() {
          return new FailureHandlingAuthenticationEntryPoint(objectMapper);
     }

     // @formatter:off
     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .csrf().disable()
               .cors()
               .and()
                    .exceptionHandling()
                         .authenticationEntryPoint(authenticationEntryPoint())
               .and()
                    .authorizeRequests()
                         .antMatchers(AUTH_WHITELIST).permitAll()
                         .antMatchers(HttpMethod.OPTIONS, ANY_PATH).permitAll()
                         .antMatchers(HttpMethod.GET, "/rooms").permitAll()
                         .anyRequest().authenticated()
               .and()
                    .sessionManagement()
                         .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
          ;
     }
     // @formatter:on
}
