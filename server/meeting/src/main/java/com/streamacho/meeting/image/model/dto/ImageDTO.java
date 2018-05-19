package com.streamacho.meeting.image.model.dto;

import com.streamacho.meeting.config.annotation.Base64Image;
import com.streamacho.meeting.image.model.enumeration.ImageExtension;
import lombok.Data;

import javax.validation.constraints.NotNull;

import static com.streamacho.meeting.config.constraints.RoomImageConstraints.MAX_HEIGHT;
import static com.streamacho.meeting.config.constraints.RoomImageConstraints.MAX_WIDTH;

@Data
public class ImageDTO {

     @Base64Image(maxWidth = MAX_WIDTH, maxHeight = MAX_HEIGHT)
     private String base64;

     @NotNull
     private ImageExtension extension;
}
