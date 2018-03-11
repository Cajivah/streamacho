package com.streamacho.api.user.service;

import com.streamacho.api.config.security.mapper.SecurityMapper;
import com.streamacho.api.config.security.model.UserPrincipal;
import com.streamacho.api.user.exception.Fault;
import com.streamacho.api.user.exception.PasswordUpdateException;
import com.streamacho.api.user.mapper.LoginMapper;
import com.streamacho.api.user.mapper.UserMapper;
import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.entity.HashedPassword;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserCredentialsService implements UserDetailsService {

     private final UserRepository userRepository;
     private final UserMapper userMapper;
     private final LoginMapper loginMapper;
     private final PasswordEncoder encoder;

     public void createUser(UserRegistrationDTO userRegistrationDTO) {
          final UserCredentials userCredentials = userMapper.toUser(userRegistrationDTO);
          userRepository.save(userCredentials);
     }

     public UserDetailsDTO getAuthenticatedUserDetails(Authentication authentication) {
          final UserPrincipal userPrincipal = SecurityMapper.toUserPrincipal(authentication);
          return userRepository.findByUsername(userPrincipal.getUsername())
               .map(userMapper::toUserDetailsDTO)
               .orElseThrow(Fault::new);
     }

     public void changePassword(ChangePasswordDTO changePasswordDTO, Authentication authentication) {
          final UserPrincipal userPrincipal = SecurityMapper.toUserPrincipal(authentication);
          final UserCredentials userCredentials = findByUsername(userPrincipal.getUsername());
          final String currentPassword = changePasswordDTO.getCurrentPassword();
          if (validatePassword(currentPassword, userCredentials.getPassword())) {
               persistPasswordUpdate(changePasswordDTO, userCredentials);
          } else {
               throw PasswordUpdateException.of();
          }
     }

     public UserCredentials findByUsername(String username) {
          return userRepository.findByUsername(username)
               .orElseThrow(Fault::new);
     }

     private boolean validatePassword(String password, HashedPassword hashedPassword) {
          return encoder.matches(password, hashedPassword.getValue());
     }

     private void persistPasswordUpdate(ChangePasswordDTO changePasswordDTO,
                                        UserCredentials userCredentials) {
          final UserCredentials updatedUser =
               userMapper.changePassword(changePasswordDTO, userCredentials);
          userRepository.save(updatedUser);
     }

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          return userRepository.findByUsername(username)
               .map(SecurityMapper::toUserDetails)
               .orElseThrow(() -> new UsernameNotFoundException(username));
     }
}
