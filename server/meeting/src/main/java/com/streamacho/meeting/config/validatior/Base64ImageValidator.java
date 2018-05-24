package com.streamacho.meeting.config.validatior;

import com.streamacho.meeting.config.annotation.Base64Image;
import io.vavr.control.Try;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.ByteArrayInputStream;

public class Base64ImageValidator implements ConstraintValidator<Base64Image, String> {

     private int maxWidth;
     private int maxHeight;

     @Override
     public void initialize(Base64Image base64Image) {
          maxWidth = base64Image.maxWidth();
          maxHeight = base64Image.maxHeight();
     }

     @Override
     public boolean isValid(String base64string, ConstraintValidatorContext cxt) {
          return Try.of(() -> Base64Utils.decodeFromString(base64string))
                    .mapTry(bytes -> ImageIO.read(new ByteArrayInputStream(bytes)))
                    .filter(image -> image.getWidth() <= maxWidth)
                    .filter(image -> image.getHeight() <= maxHeight)
                    .map(image -> true)
                    .getOrElse(false);
     }

}
