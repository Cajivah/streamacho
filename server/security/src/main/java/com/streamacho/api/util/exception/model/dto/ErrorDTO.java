package com.streamacho.api.util.exception.model.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@AllArgsConstructor(staticName = "of")
public class ErrorDTO {

     private List<String> messages;

     public ErrorDTO(String message) {
          messages = Collections.singletonList(message);
     }
}
