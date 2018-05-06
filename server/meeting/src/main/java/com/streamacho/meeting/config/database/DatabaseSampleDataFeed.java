package com.streamacho.meeting.config.database;

import com.google.common.collect.Sets;
import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.room.search.RoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("dev")
public class DatabaseSampleDataFeed implements ApplicationRunner {

     private final RoomSearchRepository roomSearchRepository;

     @Override
     public void run(ApplicationArguments args) throws Exception {
          List<Room> rooms =
               Arrays.asList(Room.builder()
                         .id(1L)
                         .organiser("malyjasiak")
                         .name("Test Room 1")
                         .description("Lorem impsum but a little bit longer")
                         .tags(Sets.newHashSet("Programming"))
                         .startAtDate(LocalDateTime.now().plusDays(2))
                         .deleted(false)
                         .createdDate(LocalDateTime.now())
                         .modifiedDate(LocalDateTime.now())
                         .build(),
                    Room.builder()
                         .id(2L)
                         .organiser("malyjasiak")
                         .name("Test Room 2")
                         .description("Lorem impsum but a little bit longer")
                         .tags(Sets.newHashSet("Life Style", "Dance", "Spain"))
                         .startAtDate(LocalDateTime.now().plusDays(2))
                         .deleted(false)
                         .createdDate(LocalDateTime.now())
                         .modifiedDate(LocalDateTime.now())
                         .build(),
                    Room.builder()
                         .id(3L)
                         .organiser("admin")
                         .name("Test Room Admin")
                         .description("Lorem impsum but a little bit longer")
                         .tags(Sets.newHashSet("Cooking", "Baking"))
                         .startAtDate(LocalDateTime.now().plusDays(2))
                         .deleted(false)
                         .createdDate(LocalDateTime.now())
                         .modifiedDate(LocalDateTime.now())
                         .build()
               );
          roomSearchRepository.saveAll(rooms);
     }
}
