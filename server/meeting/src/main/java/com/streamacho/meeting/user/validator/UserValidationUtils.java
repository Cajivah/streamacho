package com.streamacho.meeting.user.validator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserValidationUtils {

     private static final GrantedAuthority adminAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");

     public static final Predicate<UserDetails> isAdmin = user -> user.getAuthorities().contains(adminAuthority);
}
