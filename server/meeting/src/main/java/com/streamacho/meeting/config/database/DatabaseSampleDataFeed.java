package com.streamacho.meeting.config.database;

import com.streamacho.meeting.room.repository.elasticsearch.RoomSearchRepository;
import com.streamacho.meeting.room.repository.jpa.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class DatabaseSampleDataFeed implements ApplicationRunner {

     private final RoomSearchRepository roomSearchRepository;
     private final RoomRepository roomRepository;

     @Override
     public void run(ApplicationArguments args) throws Exception {
          roomRepository.findAll().forEach(roomSearchRepository::save);
     }
}
