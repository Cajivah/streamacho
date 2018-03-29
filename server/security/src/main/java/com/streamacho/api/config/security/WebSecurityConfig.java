package com.streamacho.api.config.security;

import com.streamacho.api.config.security.filter.UsernamePasswordLoginFilter;
import com.streamacho.api.config.security.logout.NopLogoutSuccessHandler;
import com.streamacho.api.config.security.mapper.WebSecurityMapper;
import com.streamacho.api.user.service.UserCredentialsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.streamacho.api.config.security.util.SecurityConstants.AUTH_WHITELIST;
import static com.streamacho.api.user.util.AvailableUserRoles.ROLE_ADMIN_SHORT;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     private final UserCredentialsService userDetailsService;
     private final WebSecurityMapper webSecurityMapper;
     private final PasswordEncoder passwordEncoder;
     private final ApplicationEventPublisher eventPublisher;

     private UsernamePasswordLoginFilter usernamePasswordLoginFilter() throws Exception {
          return new UsernamePasswordLoginFilter(
               authenticationManager(),
               eventPublisher,
               webSecurityMapper);
     }

     // @formatter:off
     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http
               .csrf().disable()
               .cors()
               .and()
                    .authorizeRequests()
                         .antMatchers(AUTH_WHITELIST).permitAll()
                         .antMatchers(
                              "/accounts",
                              "/accounts/verification")
                              .permitAll()
                         .antMatchers(
                              "/",
                              "/*/lock")
                              .hasRole(ROLE_ADMIN_SHORT)
                         .anyRequest().
                              authenticated()
               .and()
                    .addFilter(usernamePasswordLoginFilter())
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
               .and()
                    .logout()
                         .logoutSuccessHandler(new NopLogoutSuccessHandler())
          ;
     }
     // @formatter:on

     @Override
     public void configure(AuthenticationManagerBuilder auth) throws Exception {
          auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
     }
}
