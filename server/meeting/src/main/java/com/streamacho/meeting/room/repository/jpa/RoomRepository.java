package com.streamacho.meeting.room.repository.jpa;

import com.streamacho.meeting.room.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setDeleted(true);
          save(room);
     }

     Optional<Room> findOneByIdAndDeletedFalse(Long id);
}
