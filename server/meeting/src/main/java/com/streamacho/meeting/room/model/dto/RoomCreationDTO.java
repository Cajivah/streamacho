package com.streamacho.meeting.room.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomCreationDTO {

     @NotBlank
     private String name;

     @NotBlank
     private String description;

     @FutureOrPresent
     private String startAt;

     private Collection<String> tags;
}
