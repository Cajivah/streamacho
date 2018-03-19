package com.streamacho.api.user.service;

import com.streamacho.api.config.security.mapper.WebSecurityMapper;
import com.streamacho.api.user.mapper.LoginMapper;
import com.streamacho.api.user.model.dto.LoginCompleteDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.model.entity.UserLogin;
import com.streamacho.api.user.repository.UserLoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserLoginService {

     private final UserCredentialsService userCredentialsService;
     private final UserLoginRepository userLoginRepository;
     private final LoginMapper loginMapper;
     private final WebSecurityMapper webSecurityMapper;

     public void updateLastLoginDate(LoginCompleteDTO loginCompleteDTO) {
          final User user =
               webSecurityMapper.toPrincipal(loginCompleteDTO.getAuthentication());
          final UserCredentials userCredentials =
               userCredentialsService.findByUsername(user.getUsername());
          final UserLogin userLogin = loginMapper.toUserLogin(loginCompleteDTO, userCredentials);
          userLoginRepository.save(userLogin);
     }
}
