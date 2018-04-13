package com.streamacho.api.config.security.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.dto.LoginRequestDetailsDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.entity.Role;
import com.streamacho.api.user.model.entity.UserCredentials;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class WebSecurityMapper {

     ObjectMapper objectMapper;

     @Autowired
     public void setObjectMapper(ObjectMapper objectMapper) {
          this.objectMapper = objectMapper;
     }

     public UserDetails toUserDetails(UserCredentials userCredentials) {
          return User.builder()
               .username(userCredentials.getUsername())
               .password(userCredentials.getPassword().getValue())
               .authorities(toGrantedAuthorities(userCredentials.getRoles()))
               .disabled(!userCredentials.isVerified())
               .accountLocked(userCredentials.isLocked())
               .build();
     }

     public abstract Collection<GrantedAuthority> toGrantedAuthorities(Collection<Role> roles);

     public GrantedAuthority toGrantedAuthority(Role role) {
          return new SimpleGrantedAuthority(role.getName());
     }

     public User toPrincipal(Authentication authentication) {
          return (User) authentication.getPrincipal();
     }

     public LoginRequestDetailsDTO toLoginRequestDetailsDTO(HttpServletRequest request) {
          return LoginRequestDetailsDTO.builder()
               .ip(request.getRemoteAddr())
               .userAgent(request.getHeader(HttpHeaders.USER_AGENT))
               .build();
     }

     public LoginCompleteDTO toLoginCompleteDTO(HttpServletRequest request,
                                                 Authentication authentication) {
          final LoginRequestDetailsDTO loginRequestDetailsDTO = toLoginRequestDetailsDTO(request);
          return LoginCompleteDTO.builder()
               .requestDetails(loginRequestDetailsDTO)
               .authentication(authentication)
               .build();
     }

     public String toJSON(UserDetailsDTO user) throws JsonProcessingException {
          return objectMapper.writeValueAsString(user);
     }
}
