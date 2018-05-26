package com.streamacho.api.user.model.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class EmailDTO {

     @Email
     private String email;
}
