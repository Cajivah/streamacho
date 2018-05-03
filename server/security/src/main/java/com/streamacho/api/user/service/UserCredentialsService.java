package com.streamacho.api.user.service;

import com.streamacho.api.config.security.mapper.WebSecurityMapper;
import com.streamacho.api.user.exception.PasswordsMatchException;
import com.streamacho.api.user.exception.UserNotFoundException;
import com.streamacho.api.user.mapper.UserMapper;
import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.dto.LockUserDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.dto.UserRegistrationDTO;
import com.streamacho.api.user.model.entity.HashedPassword;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     private final RoleService roleService;

     @Override
     public UserDetails loadUserByUsername(String username) {
          return userRepository.findByUsernameIgnoreCaseAndVerifiedTrue(username)
               .map(webSecurityMapper::toUserDetails)
               .orElseThrow(() -> new UsernameNotFoundException(username));
     }

     public UserCredentials findByUsername(String username) {
          return userRepository.findByUsername(username)
               .orElseThrow(UserNotFoundException::of);
     }

     public UserCredentials findById(Long id) {
          return userRepository.findById(id)
               .orElseThrow(UserNotFoundException::of);
     }

     public Page<UserCredentials> findByUsernameOrEmail(String username, String email, Pageable pageable) {
          return userRepository.findAllByUsernameLikeOrEmailLike(username, email, pageable);
     }

     public Page<UserDetailsDTO> getUsersDTO(String username, String email, Pageable pageable) {
          final Page<UserCredentials> usersPage = findByUsernameOrEmail(username, email, pageable);
          return usersPage.map(userMapper::toUserDetailsDTO);
     }

     public UserDetailsDTO createUser(UserRegistrationDTO userRegistrationDTO) {
          final UserCredentials userCredentials = userMapper.toUser(userRegistrationDTO);
          final UserCredentials withRole = roleService.assignDefaultRole(userCredentials);
          final UserCredentials user = userRepository.save(withRole);
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

     public void updateUserLock(Long userId, LockUserDTO lockUserDTO) {
          final UserCredentials user = findById(userId);
          user.setLocked(lockUserDTO.isLocked());
          userRepository.save(user);
     }
}
