package com.streamacho.api.user.util;

import java.time.LocalDateTime;

public class VerificationTokenUtils {

     public static final Long EXPIRES_AFTER_DAYS = 7L;

     public static LocalDateTime getEarliestValidCreationDate() {
          return LocalDateTime.now().minusDays(EXPIRES_AFTER_DAYS);
     }
}
