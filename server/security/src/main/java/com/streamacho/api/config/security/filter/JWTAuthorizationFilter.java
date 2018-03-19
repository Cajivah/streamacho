package com.streamacho.api.config.security.filter;

import com.streamacho.api.config.security.util.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.streamacho.api.config.security.util.TokenProvider.isJWTTokenHeader;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

     private final TokenProvider tokenProvider;

     public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                   TokenProvider tokenProvider) {
          super(authenticationManager);
          this.tokenProvider = tokenProvider;
     }

     private static boolean hasJWTTokenHeader(HttpServletRequest request) {
          final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
          return isJWTTokenHeader.test(header);
     }

     @Override
     protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws IOException, ServletException {
          if (hasJWTTokenHeader(request)) {
               setAuthentication(request);
          }

          filterChain.doFilter(request, response);
     }

     private void setAuthentication(HttpServletRequest request) {
          final Authentication authentication = tokenProvider.getAuthentication(request);
          SecurityContextHolder.getContext().setAuthentication(authentication);
     }
}
