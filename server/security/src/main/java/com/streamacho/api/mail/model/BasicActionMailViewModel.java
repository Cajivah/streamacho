package com.streamacho.api.mail.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasicActionMailViewModel {

     private String actionUrl;
     private String startingGreeter;
}
