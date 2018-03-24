package com.streamacho.api.user.repository;

import com.streamacho.api.user.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findOneByName(String name);
}
