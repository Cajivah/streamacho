package com.streamacho.meeting.room.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "meeting", type = "room")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Room {

     @Id
     @GeneratedValue
     private Long id;

     @Field
     private String name;

     @Field
     private String description;

     private LocalDateTime startAtDate;

     @Field
     private String organiser;

     @Builder.Default
     @ElementCollection
     @Field
     private Set<String> tags = new HashSet<>();

     private boolean closed;

     private boolean deleted;

     @CreatedDate
     private LocalDateTime createdDate;

     @LastModifiedDate
     private LocalDateTime modifiedDate;
}
