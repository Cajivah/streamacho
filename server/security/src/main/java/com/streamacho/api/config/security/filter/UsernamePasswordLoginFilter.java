package com.streamacho.api.config.security.filter;

import com.streamacho.api.config.security.exception.JWTCreationException;
import com.streamacho.api.config.security.util.TokenProvider;
import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.event.OnLoginCompleteEvent;
import io.vavr.control.Try;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.streamacho.api.config.security.util.SecurityConstants.TOKEN_PREFIX;

public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter {

     private final TokenProvider tokenProvider;

     public UsernamePasswordLoginFilter(AuthenticationManager authenticationManager,
                                        ApplicationEventPublisher eventPublisher,
                                        TokenProvider tokenProvider) {
          this.tokenProvider = tokenProvider;
          this.setAuthenticationManager(authenticationManager);
          this.setApplicationEventPublisher(eventPublisher);
     }

     @Override
     protected void successfulAuthentication(HttpServletRequest request,
                                             HttpServletResponse response,
                                             FilterChain chain,
                                             Authentication authentication) {
          createAndAddToken(response, authentication);
          fireSuccessfulLoginEvent(request, authentication);
     }

     private void createAndAddToken(HttpServletResponse response, Authentication authentication) {
          Try.of(() -> tokenProvider.createSignedJWTHeader(authentication))
               .andThen(token -> addAuthorizationHeader(response, token))
               .getOrElseThrow(JWTCreationException::ofThrowable);
     }

     private void addAuthorizationHeader(HttpServletResponse response, String token) {
          response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + token);
     }

     private void fireSuccessfulLoginEvent(HttpServletRequest request, Authentication authentication) {
          final LoginCompleteDTO loginCompleteDTO = LoginCompleteDTO.builder()
               .request(request)
               .authentication(authentication)
               .build();
          eventPublisher.publishEvent(new OnLoginCompleteEvent(this.getClass(), loginCompleteDTO));
     }
}
