package com.streamacho.meeting.room.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum RoomStatus {

     PLANNED,
     LIVE,
     COMPLETED,
     WASTED;

     private static final List<RoomStatus> valuesAsList = Arrays.asList(RoomStatus.values());

     public static List<RoomStatus> valuesAsList() {
          return RoomStatus.valuesAsList;
     }
}
