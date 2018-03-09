package com.streamacho.api.util.annotation.matchingPasswords;

import com.streamacho.api.user.model.dto.PasswordPairDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingPasswordsConstrainValidator
     implements ConstraintValidator<MatchingPasswords, Object> {

     @Override
     public void initialize(MatchingPasswords constraintAnnotation) {
     }

     @Override
     public boolean isValid(Object obj, ConstraintValidatorContext context) {
          PasswordPairDTO passwordPair = (PasswordPairDTO) obj;
          return passwordPair.getPassword().equals(passwordPair.getMatchingPassword());
     }
}
