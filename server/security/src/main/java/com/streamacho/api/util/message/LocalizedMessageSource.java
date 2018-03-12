package com.streamacho.api.util.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Getter
@AllArgsConstructor
public class LocalizedMessageSource {

     private MessageSource messageSource;
     private Locale locale;

     public String get(String key) {
          return messageSource.getMessage(key, null, locale);
     }
}
