package com.streamacho.meeting.room.search;

import com.streamacho.meeting.room.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomSearchRepository extends ElasticsearchRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setDeleted(true);
          save(room);
     }

     @Query("{\"bool\": {\"must\": [{\"match\": {\"deleted\": false}},{\"multi_match\":"
          + "{\"fields\": [\"name\", \"organiser\", \"description\", \"tags\"],\"query\": \"?0\","
          + "\"fuzziness\": \"AUTO\"}}]}}")
     Page<Room> fuzzySearchNonDeleted(String query, Pageable pageable);

}
