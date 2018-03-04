package com.streamacho.api.config.security.service;

import com.streamacho.api.config.security.mapper.SecurityMapper;
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
public class UserAuthDetailsService implements UserDetailsService {

     private final UserRepository userRepository;
     private final PasswordEncoder encoder;

     @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          return userRepository.findByUsername(username)
               .map(SecurityMapper::toUserDetails)
               .orElseThrow(() -> new UsernameNotFoundException(username));
     }


     public boolean validatePassword(String password, HashedPassword hashedPassword) {
          return encoder.matches(password, hashedPassword.getValue());
     }
}
