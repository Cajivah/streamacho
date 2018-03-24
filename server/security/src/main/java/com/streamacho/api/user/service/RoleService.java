package com.streamacho.api.user.service;

import com.streamacho.api.user.exception.Fault;
import com.streamacho.api.user.model.entity.Role;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static com.streamacho.api.user.util.AvailableUserRoles.ROLE_USER;

@Service
@AllArgsConstructor
public class RoleService {

     private final RoleRepository roleRepository;

     public UserCredentials assignDefaultRole(UserCredentials userCredentials) {
          final Role defaultRole = findOneByName(ROLE_USER);
          userCredentials.setRoles(Collections.singleton(defaultRole));
          return userCredentials;
     }

     private Role findOneByName(String name) {
          return roleRepository
               .findOneByName(name)
               .orElseThrow(Fault::new);
     }
}
