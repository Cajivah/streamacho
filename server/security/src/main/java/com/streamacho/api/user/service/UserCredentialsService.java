package com.streamacho.api.user.service;

import com.streamacho.api.config.security.mapper.WebSecurityMapper;
import com.streamacho.api.user.exception.Fault;
import com.streamacho.api.user.exception.PasswordsMatchException;
import com.streamacho.api.user.mapper.UserMapper;
import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.entity.HashedPassword;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
     private final WebSecurityMapper webSecurityMapper;
     private final PasswordEncoder encoder;

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          return userRepository.findByUsernameIgnoreCaseAndVerifiedTrue(username)
               .map(webSecurityMapper::toUserDetails)
               .orElseThrow(() -> new UsernameNotFoundException(username));
     }

     public UserDetailsDTO createUser(UserRegistrationDTO userRegistrationDTO) {
          final UserCredentials userCredentials = userMapper.toUser(userRegistrationDTO);
          final UserCredentials user = userRepository.save(userCredentials);
          return userMapper.toUserDetailsDTO(user);
     }

     public UserDetailsDTO getAuthenticatedUserDetails(UserDetails issuer) {
          final UserCredentials user = findByUsername(issuer.getUsername());
          return userMapper.toUserDetailsDTO(user);
     }

     public void changePassword(ChangePasswordDTO changePasswordDTO, UserDetails issuer) {
          final UserCredentials user = findByUsername(issuer.getUsername());
          final String currentPassword = changePasswordDTO.getCurrentPassword();
          checkPasswordMatch(currentPassword, user.getPassword());
          persistPasswordUpdate(changePasswordDTO, user);
     }

     public UserCredentials findByUsername(String username) {
          return userRepository.findByUsername(username)
               .orElseThrow(Fault::new);
     }

     private void checkPasswordMatch(String password, HashedPassword hashedPassword) {
          if (!encoder.matches(password, hashedPassword.getValue())) {
               throw PasswordsMatchException.of();
          }
     }

     private void persistPasswordUpdate(ChangePasswordDTO changePasswordDTO,
                                        UserCredentials userCredentials) {
          final UserCredentials updatedUser =
               userMapper.updatePassword(changePasswordDTO, userCredentials);
          userRepository.save(updatedUser);
     }

     public void verifyUser(UserCredentials user) {
          user.setVerified(true);
          userRepository.save(user);
     }
}
