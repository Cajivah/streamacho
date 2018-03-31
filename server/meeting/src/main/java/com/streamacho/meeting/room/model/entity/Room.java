package com.streamacho.meeting.room.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Room {

     @Id
     @GeneratedValue
     private Long id;

     private String name;

     private String description;

     private LocalDateTime startAt;

     private String organiser;

     @Builder.Default
     @ElementCollection
     private Set<String> tags = new HashSet<>();

     private boolean closed;

     private boolean deleted;

     @CreatedDate
     private LocalDateTime created;

     @LastModifiedDate
     private LocalDateTime modified;
}
