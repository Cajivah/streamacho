package com.streamacho.api.config.security.filter;

import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.event.OnLoginCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter {


     public UsernamePasswordLoginFilter(AuthenticationManager authenticationManager,
                                        ApplicationEventPublisher eventPublisher) {
          this.setAuthenticationManager(authenticationManager);
          this.setApplicationEventPublisher(eventPublisher);
     }

     @Override
     protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                             Authentication authentication) {
          SecurityContextHolder.getContext().setAuthentication(authentication);
          response.setStatus(HttpServletResponse.SC_OK);
          fireSuccessfulLoginEvent(request, authentication);
     }

     @Override
     protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                               AuthenticationException failed) throws IOException {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
               "Authentication Failed");
     }

     private void fireSuccessfulLoginEvent(HttpServletRequest request, Authentication authentication) {
          final LoginCompleteDTO loginCompleteDTO = LoginCompleteDTO.builder()
               .request(request)
               .authentication(authentication)
               .build();
          eventPublisher.publishEvent(new OnLoginCompleteEvent(this.getClass(), loginCompleteDTO));
     }
}
