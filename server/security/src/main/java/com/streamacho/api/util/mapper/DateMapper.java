package com.streamacho.api.util.mapper;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface DateMapper {

     default ZonedDateTime toZonedDateTime(LocalDateTime local) {
          return toZonedDateTime(local, ZoneId.systemDefault());
     }

     default ZonedDateTime toZonedDateTime(LocalDateTime local, ZoneId zone) {
          return ZonedDateTime.of(local, zone);
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
