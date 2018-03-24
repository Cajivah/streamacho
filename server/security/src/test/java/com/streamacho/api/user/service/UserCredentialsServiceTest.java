package com.streamacho.api.user.service;

import com.streamacho.api.extension.MockitoExtension;
import com.streamacho.api.factory.AuthenticationFactory;
import com.streamacho.api.factory.UserCredentialsFactory;
import com.streamacho.api.user.exception.PasswordsMatchException;
import com.streamacho.api.user.mapper.UserMapper;
import com.streamacho.api.user.model.dto.ChangePasswordDTO;
import com.streamacho.api.user.model.entity.HashedPassword;
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
import org.mockito.Spy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserCredentialsServiceTest {

     @Mock
     private UserMapper userMapper;
     @Mock
     private UserRepository userRepository;
     @Spy
     private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

     @InjectMocks
     private UserCredentialsService sut;

     @Nested
     class ChangePassword {

          private String currentPassword = "KubaKuba0-";
          private HashedPassword currentPasswordHashed =
               HashedPassword.ofHashed("$2a$10$5SEEZxF3UwGYqgiTEkIXNOx7O16mvXu/zP4haioE9QwC026k8RuR.");
          private String wrongPassword = "WrongPass13*()";
          private String username = "TestUsername";

          private ChangePasswordDTO correctChangePasswordDTO =
               UserCredentialsFactory.createChangePasswordDTO(currentPassword);

          private ChangePasswordDTO wrongChangePasswordDTO =
               UserCredentialsFactory.createChangePasswordDTO(wrongPassword);

          private UserCredentials userCredentials =
               UserCredentialsFactory.createUserCredentials(username, currentPasswordHashed);

          private UserDetails issuer =
               AuthenticationFactory.createIssuer(username, currentPasswordHashed);

          @BeforeEach
          public void setUp() {
               Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(userCredentials));
               Mockito.when(userMapper.updatePassword(any(ChangePasswordDTO.class), any())).thenReturn(userCredentials);
          }

          @Test
          @DisplayName("Should call save when current passwords are matching")
          void callIfMatch() {
               sut.changePassword(correctChangePasswordDTO, issuer);
               Mockito.verify(userRepository, Mockito.times(1)).save(any());
          }

          @Test
          @DisplayName("Should throw exception if passwords does not match")
          void throwIfNotMatching() {
               assertThrows(PasswordsMatchException.class,
                    () -> sut.changePassword(wrongChangePasswordDTO, issuer));
          }
     }
}