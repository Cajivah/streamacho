package com.streamacho.api.user.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDetailsDTO implements Serializable {

     private String username;
     private String email;
}
