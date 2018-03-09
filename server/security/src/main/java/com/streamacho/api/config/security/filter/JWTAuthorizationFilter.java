package com.streamacho.api.config.security.filter;

import com.streamacho.api.config.security.exception.InvalidJWTException;
import com.streamacho.api.config.security.model.UserPrincipal;
import io.vavr.control.Try;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.streamacho.api.config.security.util.SecurityConstants.TOKEN_PREFIX;
import static com.streamacho.api.config.security.util.TokenUtils.isJWTTokenHeader;
import static com.streamacho.api.config.security.util.TokenUtils.verifyAndExtractUserPrincipal;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

     public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
          super(authenticationManager);
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
          final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
          SecurityContextHolder.getContext().setAuthentication(authentication);
     }

     private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
          final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
          final String jwtToken = header.substring(TOKEN_PREFIX.length());
          return Try.of(() -> verifyAndExtractUserPrincipal(jwtToken))
               .mapTry(this::createAuthenticationToken)
               .getOrElseThrow(InvalidJWTException::ofThrowable);
     }

     private UsernamePasswordAuthenticationToken createAuthenticationToken(UserPrincipal principal) {
          return new UsernamePasswordAuthenticationToken(principal, null, new ArrayList<>());
     }
}
