package com.streamacho.api.user.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableUserRoles {

     public static final String PREFIX = "ROLE_";
     public static final String ROLE_USER_SHORT = "USER";
     public static final String ROLE_USER = PREFIX + ROLE_USER_SHORT;
     public static final String ROLE_ADMIN_SHORT = "ADMIN";
     public static final String ROLE_ADMIN = PREFIX + ROLE_ADMIN_SHORT;
}
