package com.streamacho.api.user.model.dto;

import lombok.Data;

import java.util.Locale;

@Data
public class VerificationMailDTO {

     private String address;
     private String username;
     private String token;
     private Locale locale;
}
