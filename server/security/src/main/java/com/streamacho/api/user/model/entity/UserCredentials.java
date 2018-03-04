package com.streamacho.api.user.model.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserCredentials {

     @Id
     @GeneratedValue
     private Long id;
     @Column(unique = true, nullable = false)
     private String username;
     @Column(unique = true, nullable = false)
     @Email
     private String email;
     @Embedded
     private HashedPassword password;
     @CreatedDate
     private LocalDateTime created;
     @LastModifiedDate
     private LocalDateTime modified;
}
