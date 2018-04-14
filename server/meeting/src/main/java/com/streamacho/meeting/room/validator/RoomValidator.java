package com.streamacho.meeting.room.validator;

import com.streamacho.meeting.room.model.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import static com.streamacho.meeting.user.validator.UserValidationUtils.isAdmin;

@RequiredArgsConstructor(staticName = "of")
public class RoomValidator {

     private final Room room;
     private boolean valid = true;

     public boolean isValid() {
          return valid;
     }

     public RoomValidator isModifiableBy(UserDetails user) {
          if (valid && !isOrganiserOrAdmin(user)) {
               valid = false;
          }
          return this;
     }

     public RoomValidator isStartAtDateBeforeNow() {
          if (valid && !room.getStartAt().isBefore(LocalDateTime.now())) {
               valid = false;
          }
          return this;
     }

     public <X extends Throwable> void ifInvalidThrow(Supplier<? extends X> exceptionSupplier) throws X {
          if (!valid) {
               throw exceptionSupplier.get();
          }
     }

     private boolean isOrganiserOrAdmin(UserDetails user) {
          return room.getOrganiser().equals(user.getUsername()) || isAdmin.test(user);
     }
}
