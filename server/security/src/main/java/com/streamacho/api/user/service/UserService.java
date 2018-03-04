package com.streamacho.api.user.service;

import com.streamacho.api.config.security.mapper.SecurityMapper;
import com.streamacho.api.config.security.model.UserPrincipal;
import com.streamacho.api.config.security.service.UserAuthDetailsService;
import com.streamacho.api.user.exception.Fault;
import com.streamacho.api.user.exception.PasswordUpdateException;
import com.streamacho.api.user.mapper.UserMapper;
import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

     private final UserAuthDetailsService userAuthDetailsService;
     private final UserRepository userRepository;
     private final UserMapper userMapper;

     public void createUser(UserRegistrationDTO userRegistrationDTO) {
          final UserCredentials userCredentials = userMapper.toUser(userRegistrationDTO);
          userRepository.save(userCredentials);
     }

     public boolean updateLastLogin(Object principal) {
          // todo implement when working on security
          // https://stackoverflow.com/questions/27956134/spring-security-update-last-login-date-on-authentication-success
          // http://www.baeldung.com/spring-security-track-logged-in-users
          return false;
     }

     public UserDetailsDTO getAuthenticatedUserDetails(Authentication authentication) {
          final UserPrincipal userPrincipal = SecurityMapper.toUserPrincipal(authentication);
          return userRepository.findByUsername(userPrincipal.getUsername())
               .map(userMapper::toUserDetailsDTO)
               .orElseThrow(Fault::new);
     }

     public void changePassword(ChangePasswordDTO changePasswordDTO, Authentication authentication) {
          final UserPrincipal userPrincipal = SecurityMapper.toUserPrincipal(authentication);
          final UserCredentials userCredentials =
               userRepository.findByUsername(userPrincipal.getUsername())
                    .orElseThrow(Fault::new);
          final String currentPassword = changePasswordDTO.getCurrentPassword();
          if (userAuthDetailsService.validatePassword(currentPassword, userCredentials.getPassword())) {
               persistPasswordUpdate(changePasswordDTO, userCredentials);
          } else {
               throw PasswordUpdateException.of();
          }
     }

     private void persistPasswordUpdate(ChangePasswordDTO changePasswordDTO,
                                        UserCredentials userCredentials) {
          final UserCredentials updatedUser =
               userMapper.changePassword(changePasswordDTO, userCredentials);
          userRepository.save(updatedUser);
     }
}
