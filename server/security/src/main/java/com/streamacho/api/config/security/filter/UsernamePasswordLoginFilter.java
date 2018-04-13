package com.streamacho.api.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamacho.api.config.security.mapper.WebSecurityMapper;
import com.streamacho.api.config.security.util.HttpServletResponseDecorator;
import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.event.OnLoginCompleteEvent;
import com.streamacho.api.user.service.UserCredentialsService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter {

     private final WebSecurityMapper webSecurityMapper;
     private final UserCredentialsService userCredentialsService;

     public UsernamePasswordLoginFilter(AuthenticationManager authenticationManager,
                                        ApplicationEventPublisher eventPublisher,
                                        WebSecurityMapper webSecurityMapper,
                                        UserCredentialsService userCredentialsService) {
          this.setAuthenticationManager(authenticationManager);
          this.setApplicationEventPublisher(eventPublisher);
          this.webSecurityMapper = webSecurityMapper;
          this.userCredentialsService = userCredentialsService;
     }

     @Override
     protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                             Authentication authentication) throws IOException {
          SecurityContextHolder.getContext().setAuthentication(authentication);
          setResponse(
               new HttpServletResponseDecorator(response),
               (UserDetails) authentication.getPrincipal());
          fireSuccessfulLoginEvent(request, authentication);
     }

     private void setResponse(HttpServletResponseDecorator response,
                              UserDetails principal) throws IOException {
          setUserBody(response, principal);
          response.setStatusOK();
     }

     private void setUserBody(HttpServletResponseDecorator response, UserDetails principal) throws IOException {
          final UserDetailsDTO user = userCredentialsService.getAuthenticatedUserDetails(principal);
          final String userJSON = webSecurityMapper.toJSON(user);
          response.setJSONBody(userJSON);
     }

     @Override
     protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                               AuthenticationException failed) throws IOException {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
               "Authentication Failed");
     }

     private void fireSuccessfulLoginEvent(HttpServletRequest request, Authentication authentication) {
          final LoginCompleteDTO loginCompleteDTO =
               webSecurityMapper.toLoginCompleteDTO(request, authentication);
          eventPublisher.publishEvent(new OnLoginCompleteEvent(this.getClass(), loginCompleteDTO));
     }
}
