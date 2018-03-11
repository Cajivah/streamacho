package com.streamacho.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamacho.api.config.security.filter.JWTAuthorizationFilter;
import com.streamacho.api.config.security.filter.UsernamePasswordLoginFilter;
import com.streamacho.api.user.service.UserCredentialsService;
import com.streamacho.api.user.service.UserLoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.streamacho.api.config.security.util.SecurityConstants.AUTH_WHITELIST;
import static com.streamacho.api.config.security.util.SecurityConstants.REGISTRATION_URL;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     private final UserCredentialsService userDetailsService;
     private final UserLoginService userLoginService;
     private final PasswordEncoder passwordEncoder;
     private final ObjectMapper objectMapper;

     private UsernamePasswordLoginFilter usernamePasswordAuthenticationFilter() throws Exception {
          return new UsernamePasswordLoginFilter(
               authenticationManager(),
               userLoginService,
               objectMapper);
     }

     private JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
          return new JWTAuthorizationFilter(authenticationManager());
     }

     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .csrf().disable()
               .cors()
               .and()
               .authorizeRequests()
               .antMatchers(AUTH_WHITELIST).permitAll()
               .antMatchers(HttpMethod.POST, REGISTRATION_URL).permitAll()
               .anyRequest().authenticated()
               .and()
               .addFilter(usernamePasswordAuthenticationFilter())
               .addFilter(jwtAuthorizationFilter())
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          ;
     }

     @Override
     public void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
     }
}
