package com.streamacho.meeting.config.annotation;

import com.streamacho.meeting.config.validatior.Base64ImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = Base64ImageValidator.class)
@Target( { ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64Image {

     String message() default "Invalid base64 string or image violates dimension constraints";

     int maxWidth();

     int maxHeight();

     Class<?>[] groups() default {};

     Class<? extends Payload>[] payload() default {};
}
