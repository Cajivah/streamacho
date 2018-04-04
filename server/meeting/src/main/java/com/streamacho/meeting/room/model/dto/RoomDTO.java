package com.streamacho.meeting.room.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {

     private Long id;
     private String name;
     private String description;
     private ZonedDateTime startAt;
     private String organiser;
     private List<String> tags;
     private boolean closed;
}