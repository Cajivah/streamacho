package com.streamacho.api.user.repository;

import com.streamacho.api.user.model.entity.UserCredentials;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserCredentials, Long> {

     Optional<UserCredentials> findByUsername(String username);

     Optional<UserCredentials> findByUsernameIgnoreCaseAndVerifiedTrue(String username);

     Page<UserCredentials> findAllByUsernameLikeOrEmailLike(String username, String email,
                                                            Pageable pageable);
}
