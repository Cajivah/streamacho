package com.streamacho.meeting.image.mapper;

import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class CloudinaryMapper {

     public String toSecureUrl(Map cloudinaryResponse) {
          return (String) cloudinaryResponse.get("secure_url");
     }
}
