package com.streamacho.meeting.room.search;

import com.streamacho.meeting.room.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import static com.streamacho.meeting.room.model.enumeration.RoomStatus.WASTED;

@Repository
public interface RoomSearchRepository extends ElasticsearchRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setStatus(WASTED);
          save(room);
     }

     @Query("{\"bool\": {\"must\": [{\"multi_match\" : {\"fields\": [\"name\", \"organiser\","
          + "\"description\", \"tags\"],\"query\": \"test\",\"fuzziness\": \"AUTO\"}},"
          + "{\"constant_score\": {\"filter\" : {\"term\" : {\"status\" : \"planned\" }}}}]}}")
     Page<Room> fuzzySearchPlanned(String query, Pageable pageable);
}
