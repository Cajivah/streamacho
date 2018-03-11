package com.streamacho.api.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

     @Builder.Default
     @Transient
     private Set<UserLogin> logins = new HashSet<>();

     @LastModifiedDate
     private LocalDateTime modified;
}
