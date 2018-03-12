package com.streamacho.api.config.security;

import com.streamacho.api.config.security.filter.JWTAuthorizationFilter;
import com.streamacho.api.config.security.filter.UsernamePasswordLoginFilter;
import com.streamacho.api.config.security.util.TokenProvider;
import com.streamacho.api.user.service.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import static com.streamacho.api.config.security.util.SecurityConstants.VERIFICATION_URL;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     private final UserCredentialsService userDetailsService;
     private final PasswordEncoder passwordEncoder;
     private final TokenProvider tokenProvider;
     private final ApplicationEventPublisher eventPublisher;

     private UsernamePasswordLoginFilter usernamePasswordLoginFilter() throws Exception {
          return new UsernamePasswordLoginFilter(
               authenticationManager(),
               eventPublisher,
               tokenProvider);
     }

     private JWTAuthorizationFilter jwtAuthorizationFilter() throws Exception {
          return new JWTAuthorizationFilter(authenticationManager(), tokenProvider);
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
               .antMatchers(HttpMethod.PATCH, VERIFICATION_URL).permitAll()
               .anyRequest().authenticated()
               .and()
               .addFilter(usernamePasswordLoginFilter())
               .addFilter(jwtAuthorizationFilter())
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          ;
     }

     @Override
     public void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
     }
}
