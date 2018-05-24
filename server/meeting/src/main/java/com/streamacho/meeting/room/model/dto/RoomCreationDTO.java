package com.streamacho.meeting.room.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.streamacho.meeting.image.model.dto.ImageDTO;
import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
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
     private ZonedDateTime startAt;

     private Collection<String> tags;

     @Nullable
     @JsonProperty("logo")
     @Valid
     private ImageDTO logoDTO;
}
