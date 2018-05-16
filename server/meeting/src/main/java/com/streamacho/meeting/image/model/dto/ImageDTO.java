package com.streamacho.meeting.image.model.dto;

import com.streamacho.meeting.config.annotation.Base64;
import com.streamacho.meeting.image.model.enumeration.ImageExtension;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ImageDTO {

     @Base64
     private String base64;

     @NotBlank
     private ImageExtension extension;
}
