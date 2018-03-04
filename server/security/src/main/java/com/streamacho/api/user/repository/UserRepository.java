package com.streamacho.api.user.repository;

import com.streamacho.api.user.model.entity.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCredentials, Long> {

     Optional<UserCredentials> findByUsername(String username);
}
