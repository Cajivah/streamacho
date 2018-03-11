package com.streamacho.api.config.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.streamacho.api.config.security.exception.JWTCreationException;
import com.streamacho.api.config.security.exception.LoginFailedException;
import com.streamacho.api.config.security.util.TokenUtils;
import com.streamacho.api.user.model.dto.LastLoginDTO;
import com.streamacho.api.user.model.dto.UserLoginDTO;
import com.streamacho.api.user.service.UserLoginService;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static com.streamacho.api.config.security.util.SecurityConstants.TOKEN_PREFIX;

@AllArgsConstructor
public class UsernamePasswordLoginFilter extends UsernamePasswordAuthenticationFilter {

     private final AuthenticationManager authenticationManager;
     private final UserLoginService userLoginService;
     private final ObjectMapper objectMapper;

     @Override
     public Authentication attemptAuthentication(HttpServletRequest request,
                                                 HttpServletResponse response) {
          return Try
               .of(() -> objectMapper.readValue(request.getInputStream(), UserLoginDTO.class))
               .mapTry(this::createAuthenticationToken)
               .mapTry(authenticationManager::authenticate)
               .getOrElseThrow(LoginFailedException::ofThrowable);
     }

     private UsernamePasswordAuthenticationToken createAuthenticationToken(UserLoginDTO user) {
          return new UsernamePasswordAuthenticationToken(
               user.getUsername(),
               user.getPassword(),
               new ArrayList<>());
     }

     @Override
     protected void successfulAuthentication(HttpServletRequest req,
                                             HttpServletResponse res,
                                             FilterChain chain,
                                             Authentication auth) {
          final User user = (User) auth.getPrincipal();
          createAndAddToken(res, user);
          updateLastLogin(req, user);
     }

     private void updateLastLogin(HttpServletRequest req, User user) {
          final LastLoginDTO lastLoginDTO = LastLoginDTO.builder()
               .username(user.getUsername())
               .request(req)
               .build();
          userLoginService.updateLastLoginDate(lastLoginDTO);
     }

     private void createAndAddToken(HttpServletResponse res, User user) {
          Try.of(() -> TokenUtils.createSignedJWT(user))
               .mapTry(JWSObject::serialize)
               .andThen(token -> addAuthorizationHeader(res, token))
               .getOrElseThrow(JWTCreationException::ofThrowable);
     }

     private void addAuthorizationHeader(HttpServletResponse response, String token) {
          response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + token);
     }
}
