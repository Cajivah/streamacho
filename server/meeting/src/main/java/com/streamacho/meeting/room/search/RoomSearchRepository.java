package com.streamacho.meeting.room.search;

import com.streamacho.meeting.room.model.entity.Room;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSearchRepository extends ElasticsearchRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setDeleted(true);
          save(room);
     }
}
