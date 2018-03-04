package com.streamacho.api.user.model.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedPassword {

     @Column(length = 60, name = "password")
     String value;

     public static HashedPassword of(String plainTextPassword, PasswordEncoder passwordEncoder) {
          final String hashed = passwordEncoder.encode(plainTextPassword);
          return ofHashed(hashed);
     }

     public static HashedPassword ofHashed(String hashed) {
          return new HashedPassword(hashed);
     }
}
