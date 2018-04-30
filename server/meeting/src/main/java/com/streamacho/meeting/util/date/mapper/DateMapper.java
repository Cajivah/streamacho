package com.streamacho.meeting.util.date.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Mapper(componentModel = "spring")
public interface DateMapper {

     default ZonedDateTime toZonedDateTime(LocalDateTime local) {
          return toZonedDateTime(local, ZoneId.systemDefault());
     }

     default ZonedDateTime toZonedDateTime(LocalDateTime local, ZoneId zone) {
          return !isNull(local) ? ZonedDateTime.of(local, zone) : null;
     }

     default LocalDateTime toLocalDateTime(ZonedDateTime zoned) {
          return toLocalDateTime(zoned, ZoneId.systemDefault());
     }

     default LocalDateTime toLocalDateTime(ZonedDateTime zoned, ZoneId localZone) {
          return zoned
               .withZoneSameInstant(localZone)
               .toLocalDateTime();
     }
}
