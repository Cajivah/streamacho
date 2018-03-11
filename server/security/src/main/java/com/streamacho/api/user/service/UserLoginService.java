package com.streamacho.api.user.service;

import com.streamacho.api.user.mapper.LoginMapper;
import com.streamacho.api.user.model.dto.LastLoginDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.model.entity.UserLogin;
import com.streamacho.api.user.repository.UserLoginRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserLoginService {

     private final UserCredentialsService userCredentialsService;
     private final UserLoginRepository userLoginRepository;
     private final LoginMapper loginMapper;

     @Transactional
     public void updateLastLoginDate(LastLoginDTO lastLoginDTO) {
          final UserCredentials userCredentials =
               userCredentialsService.findByUsername(lastLoginDTO.getUsername());
          final UserLogin userLogin = loginMapper.toUserLogin(lastLoginDTO, userCredentials);
          userLoginRepository.save(userLogin);
     }
}
