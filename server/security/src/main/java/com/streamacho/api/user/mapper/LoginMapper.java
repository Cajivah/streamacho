package com.streamacho.api.user.mapper;

import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.model.entity.UserLogin;
import org.mapstruct.Mapper;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;

@Mapper(componentModel = "spring")
public interface LoginMapper {

     default UserLogin toUserLogin(LoginCompleteDTO loginCompleteDTO, UserCredentials userCredentials) {
          final HttpServletRequest request = loginCompleteDTO.getRequest();
          return UserLogin.builder()
               .userCredentials(userCredentials)
               .ip(request.getRemoteAddr())
               .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
               .build();
     }
}
