package com.streamacho.meeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeetingApplication {

     public static void main(String[] args) {
          System.setProperty("es.set.netty.runtime.available.processors", "false");
          SpringApplication.run(MeetingApplication.class, args);
     }
}
