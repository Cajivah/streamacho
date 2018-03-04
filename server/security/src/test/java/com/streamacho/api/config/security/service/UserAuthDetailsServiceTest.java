package com.streamacho.api.config.security.service;

import com.streamacho.api.user.model.entity.HashedPassword;
import com.streamacho.api.user.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserAuthDetailsServiceTest {

     private PasswordEncoder encoder = new BCryptPasswordEncoder();
     @Mock
     private UserRepository userRepository;

     private UserAuthDetailsService sut = new UserAuthDetailsService(userRepository, encoder);

     @Nested
     class ValidatePassword {

          private String password = "KubaKuba0-";
          private String bcryptHashed = "$2a$10$5SEEZxF3UwGYqgiTEkIXNOx7O16mvXu/zP4haioE9QwC026k8RuR.";
          private HashedPassword hashedPassword = HashedPassword.ofHashed(bcryptHashed);

          private String differentPassword = "different";

          @Test
          void trueIfMatches() {
               assertTrue(sut.validatePassword(password, hashedPassword));
          }

          @Test
          void falseIfDoesNotMatch() {
               assertFalse(sut.validatePassword(differentPassword, hashedPassword));
          }
     }
}