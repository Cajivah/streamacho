package com.streamacho.meeting.config.validatior;

import com.streamacho.meeting.config.annotation.Base64;
import io.vavr.control.Try;
import org.springframework.util.Base64Utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Validator implements ConstraintValidator<Base64, String> {

     @Override
     public void initialize(Base64 contactNumber) {
     }

     @Override
     public boolean isValid(String base64string, ConstraintValidatorContext cxt) {
          return Try.run(() -> Base64Utils.decodeFromString(base64string))
                    .map(aVoid -> true)
                    .getOrElse(false);
     }

}
