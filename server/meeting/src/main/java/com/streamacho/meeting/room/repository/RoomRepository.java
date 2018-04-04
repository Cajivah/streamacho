package com.streamacho.meeting.room.repository;

import com.streamacho.meeting.room.model.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setDeleted(true);
          save(room);
     }

     Page<Room> findAllByDeletedFalse(Pageable pageable);

     Optional<Room> findOneByIdAndDeletedFalse(Long id);
}