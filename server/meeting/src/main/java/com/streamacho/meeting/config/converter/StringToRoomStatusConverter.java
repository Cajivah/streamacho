package com.streamacho.meeting.config.converter;

import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import io.vavr.control.Try;
import org.springframework.core.convert.converter.Converter;

public class StringToRoomStatusConverter implements Converter<String, RoomStatus> {

     @Override
     public RoomStatus convert(String source) {
          return Try.of(() -> RoomStatus.valueOf(source.toUpperCase()))
                    .getOrNull();
     }
}
