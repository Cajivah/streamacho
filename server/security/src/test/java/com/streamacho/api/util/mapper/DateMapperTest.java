package com.streamacho.api.util.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DateMapperTest {

     DateMapper sut = new DateMapper() {
     };

     @Nested
     @DisplayName("Convert date ")
     class ConvertDate {

          ZoneId localZone = ZoneId.of("UTC+2");
          LocalDateTime localDateTime = LocalDateTime.of(
               2018, 12, 13, 15, 0, 0, 0);
          ZonedDateTime localDateTimeWithZone = ZonedDateTime.of(
               2018, 12, 13, 15, 0, 0, 0, localZone);
          ZoneId differentZone = ZoneId.of("UTC+1");
          ZonedDateTime zonedDateTime = ZonedDateTime.of(
               2018, 12, 13, 14, 0, 0, 0, differentZone);

          @Test
          @DisplayName("from zoned to local")
          void toLocal() {
               LocalDateTime converted = sut.toLocalDateTime(zonedDateTime, localZone);
               assertEquals(localDateTime, converted);
          }

          @Test
          @DisplayName("from local to zoned")
          void toZoned() {
               ZonedDateTime converted = sut.toZonedDateTime(localDateTime, localZone);
               assertEquals(localDateTimeWithZone, converted);
          }
     }
}
