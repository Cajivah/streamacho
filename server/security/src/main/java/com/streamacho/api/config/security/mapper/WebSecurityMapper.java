package com.streamacho.api.config.security.mapper;

import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.dto.LoginRequestDetailsDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Mapper(componentModel = "spring")
public interface WebSecurityMapper {

     default UserDetails toUserDetails(UserCredentials userCredentials) {
          return User.builder()
               .username(userCredentials.getUsername())
               .password(userCredentials.getPassword().getValue())
               .authorities(Collections.emptyList())
               .build();
     }

     default User toPrincipal(Authentication authentication) {
          return (User) authentication.getPrincipal();
     }

     default LoginRequestDetailsDTO toLoginRequestDetailsDTO(HttpServletRequest request) {
          return LoginRequestDetailsDTO.builder()
               .ip(request.getRemoteAddr())
               .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
               .build();
     }

     default LoginCompleteDTO toLoginCompleteDTO(HttpServletRequest request,
                                                 Authentication authentication) {
          final LoginRequestDetailsDTO loginRequestDetailsDTO = toLoginRequestDetailsDTO(request);
          return LoginCompleteDTO.builder()
               .requestDetails(loginRequestDetailsDTO)
               .authentication(authentication)
               .build();
     }
}
