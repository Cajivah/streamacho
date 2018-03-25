package com.streamacho.api.user.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class LoginRequestDetailsDTO implements Serializable {

     private String ip;
     private String userAgent;
}
