package com.streamacho.api.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role {

     @Id
     @GeneratedValue
     private Long id;

     @NotBlank
     @Column(unique = true)
     private String name;

     @ManyToMany(mappedBy = "roles")
     private Collection<UserCredentials> users;
}
