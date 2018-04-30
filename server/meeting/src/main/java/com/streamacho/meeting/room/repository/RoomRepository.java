package com.streamacho.meeting.room.repository;

import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.streamacho.meeting.room.model.enumeration.RoomStatus.WASTED;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

     default void deleteSoft(Room room) {
          room.setStatus(WASTED);
          save(room);
     }

     Page<Room> findAllByStatus(RoomStatus state, Pageable pageable);

     Optional<Room> findOneByIdAndStatus(Long id, RoomStatus state);
}
