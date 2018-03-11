package com.streamacho.api.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString(exclude = "userCredentials")
@EqualsAndHashCode(exclude = "userCredentials")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserLogin {

     @Id
     @GeneratedValue
     private Long id;

     @CreatedDate
     private LocalDateTime date;

     private String ip;

     private String userAgent;

     @ManyToOne
     private UserCredentials userCredentials;
}
