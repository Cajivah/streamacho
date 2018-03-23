package com.streamacho.api.user.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VerificationTokenUtils {

     private static final Long EXPIRES_AFTER_DAYS = 7L;

     public static LocalDateTime getEarliestValidCreationDate() {
          return LocalDateTime.now().minusDays(EXPIRES_AFTER_DAYS);
     }
}
