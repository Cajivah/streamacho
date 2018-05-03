package com.streamacho.meeting.room.repository.elasticsearch;

import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import com.streamacho.meeting.util.elasticsearch.mapper.ElasticsearchQueryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoomSearchRepository extends ElasticsearchRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setDeleted(true);
          save(room);
     }

     default Page<Room> fuzzySearchNonDeleted(String query, Collection<RoomStatus> statuses,
                                              Pageable pageable) {
          return fuzzySearchNonDeleted(query, ElasticsearchQueryMapper.toString(statuses), pageable);
     }

     @Query("{\"bool\": {\"must\": [{\"multi_match\": {\"fields\": [\"name\", \"organiser\", "
          + "\"description\", \"tags\"],\"query\": \"?0\",\"fuzziness\": \"AUTO\"}}, "
          + "{\"constant_score\" : {\"filter\" : {\"bool\": {\"must\": [{\"term\": "
          + "{\"deleted\": false}}, {\"terms\" : {\"status\" : ?1}}]}}}}]}}")
     Page<Room> fuzzySearchNonDeleted(String query, String statuses, Pageable pageable);

     default Page<Room> searchNonDeleted(Collection<RoomStatus> statuses,
                                              Pageable pageable) {
          return searchNonDeleted(ElasticsearchQueryMapper.toString(statuses), pageable);
     }

     @Query("{\"constant_score\": {\"filter\": {\"bool\": {\"must\": [{\"term\": {\"deleted\": "
          + "false}}, {\"terms\": {\"status\": ?0}}]}}}}")
     Page<Room> searchNonDeleted(String statuses, Pageable pageable);
}
