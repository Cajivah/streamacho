package com.streamacho.meeting.config.annotation;

import com.streamacho.meeting.config.validatior.Base64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = Base64Validator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64 {

     String message() default "Invalid base64 string";

     Class<?>[] groups() default {};

     Class<? extends Payload>[] payload() default {};
}
