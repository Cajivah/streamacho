package com.streamacho.meeting.room.model.entity;

import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
@Document(indexName = "meeting", type = "room")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Room {

     public Room() {
         tags = new HashSet<>();
         status = RoomStatus.PLANNED;
     }

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
     @ElementCollection(fetch = FetchType.EAGER)
     @Field
     private Set<String> tags = new HashSet<>();

     private boolean deleted;

     @Builder.Default
     @Enumerated(EnumType.STRING)
     private RoomStatus status = RoomStatus.PLANNED;

     @Nullable
     private LocalDateTime transmissionStartedAt;

     @CreatedDate
     private LocalDateTime createdDate;

     @LastModifiedDate
     private LocalDateTime modifiedDate;
}
