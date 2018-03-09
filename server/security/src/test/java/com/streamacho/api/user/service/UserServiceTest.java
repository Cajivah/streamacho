package com.streamacho.api.user.service;

import com.streamacho.api.config.security.service.UserAuthDetailsService;
import com.streamacho.api.extension.MockitoExtension;
import com.streamacho.api.factory.AuthenticationFactory;
import com.streamacho.api.factory.UserCredentialsFactory;
import com.streamacho.api.user.exception.PasswordUpdateException;
import com.streamacho.api.user.mapper.UserMapper;
import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

     @Mock
     private UserMapper userMapper;
     @Mock
     private UserRepository userRepository;
     @Mock
     private UserAuthDetailsService userAuthDetailsService;

     @InjectMocks
     private UserService sut;

     @Nested
     class ChangePassword {

          private String currentPassword = "KubaKuba10-";
          private String wrongPassword = "WrongPass13*()";
          private String username = "TestUsername";
          
          private ChangePasswordDTO correctChangePasswordDTO =
               UserCredentialsFactory.createChangePasswordDTO(currentPassword);

          private ChangePasswordDTO wrongChangePasswordDTO =
               UserCredentialsFactory.createChangePasswordDTO(wrongPassword);

          private UserCredentials userCredentials =
               UserCredentialsFactory.createUserCredentials(username);

          private Authentication authentication =
               AuthenticationFactory.createAuthentication(username);

          @BeforeEach
          public void setUp() {
               Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(userCredentials));
               Mockito.when(userAuthDetailsService.validatePassword(eq(currentPassword), any())).thenReturn(true);
               Mockito.when(userAuthDetailsService.validatePassword(eq(wrongPassword), any())).thenReturn(false);
               Mockito.when(userMapper.changePassword(any(ChangePasswordDTO.class), any())).thenReturn(userCredentials);
          }

          @Test
          @DisplayName("Should call save when current passwords are matching")
          void callIfMatch() {
               sut.changePassword(correctChangePasswordDTO, authentication);
               Mockito.verify(userRepository, Mockito.times(1)).save(any());
          }

          @Test
          @DisplayName("Should throw exception if passwords does not match")
          void throwIfNotMatching() {
               assertThrows(PasswordUpdateException.class,
                    () -> sut.changePassword(wrongChangePasswordDTO, authentication));
          }
     }
}