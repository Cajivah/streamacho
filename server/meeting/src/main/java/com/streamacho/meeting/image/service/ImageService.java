package com.streamacho.meeting.image.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.streamacho.meeting.image.mapper.CloudinaryMapper;
import com.streamacho.meeting.image.model.dto.ImageDTO;
import com.streamacho.meeting.image.model.enumeration.ImageExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {

     private final Cloudinary cloudinary;
     private final CloudinaryMapper cloudinaryMapper;

     public String upload(ImageDTO imageDTO) throws IOException {
          String payload = prepareBase64Payload(imageDTO.getBase64(), imageDTO.getExtension());
          Map response = cloudinary.uploader().upload(payload, ObjectUtils.emptyMap());
          return cloudinaryMapper.toSecureUrl(response);
     }

     private String prepareBase64Payload(String base64, ImageExtension extension) {
          return String.format("data:image/%s;base64,%s", extension, base64);
     }
}
