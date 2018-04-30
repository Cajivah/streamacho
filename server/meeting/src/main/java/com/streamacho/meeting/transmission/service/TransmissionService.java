package com.streamacho.meeting.transmission.service;

import com.streamacho.meeting.config.properties.OpenViduProperties;
import com.streamacho.meeting.room.exception.RoomNotFoundException;
import com.streamacho.meeting.room.exception.ValidationException;
import com.streamacho.meeting.room.model.entity.Room;
import com.streamacho.meeting.room.model.enumeration.RoomStatus;
import com.streamacho.meeting.room.repository.RoomRepository;
import com.streamacho.meeting.room.validator.RoomValidator;
import com.streamacho.meeting.transmission.exception.SessionDoesNotExistException;
import com.streamacho.meeting.transmission.model.dto.SessionDTO;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.TokenOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@EnableConfigurationProperties(OpenViduProperties.class)
public class TransmissionService {

     private final OpenVidu openViduServer;
     private final OpenViduProperties openViduProperties;
     private final RoomRepository roomRepository;
     private final Map<Room, Session> sessions;

     @Autowired
     public TransmissionService(OpenViduProperties openViduProperties,
                                RoomRepository roomRepository) {
          this.openViduProperties = openViduProperties;
          this.roomRepository = roomRepository;
          this.sessions = new HashMap<>();
          this.openViduServer = new OpenVidu(openViduProperties.getUrl(),
               openViduProperties.getSecret());
     }

     public SessionDTO startStream(Long roomId, UserDetails issuer) {
          Room room = roomRepository.findOneByIdAndStatus(roomId, RoomStatus.PLANNED)
                                    .orElseThrow(RoomNotFoundException::of);
          RoomValidator.of(room)
                       .isStartAtDateBeforeNow()
                       .isModifiableBy(issuer)
                       .ifInvalidThrow(ValidationException::of);

          OpenViduRole openViduRole = OpenViduRole.PUBLISHER;
          String serverData = prepareOpenViduServerData(issuer.getUsername());
          TokenOptions tokenOptions = new TokenOptions.Builder().data(serverData)
                                                                .role(openViduRole)
                                                                .build();
          Session session = openViduServer.createSession();
          String sessionId = session.getSessionId();
          String token = session.generateToken(tokenOptions);
          sessions.put(room, session);
          return new SessionDTO(token, sessionId);
     }

     private String prepareOpenViduServerData(String username) {
          return String.format("{\"serverData\": \"%s\"}", username);
     }

     public SessionDTO joinStream(Long roomId, UserDetails issuer) {
          Room room = roomRepository.findOneByIdAndStatus(roomId, RoomStatus.LIVE)
                                    .orElseThrow(RoomNotFoundException::of);
          Session session = Optional.ofNullable(sessions.get(room))
                                    .orElseThrow(SessionDoesNotExistException::of);

          OpenViduRole openViduRole = OpenViduRole.SUBSCRIBER;
          String serverData = prepareOpenViduServerData(issuer.getUsername());
          TokenOptions tokenOptions = new TokenOptions.Builder().data(serverData)
                                                                .role(openViduRole)
                                                                .build();
          String token = session.generateToken(tokenOptions);
          String sessionId = session.getSessionId();
          return new SessionDTO(token, sessionId);
     }

     public void closeStream(Long roomId, UserDetails issuer) {
          Room room = roomRepository.findOneByIdAndStatus(roomId, RoomStatus.LIVE)
                                    .orElseThrow(RoomNotFoundException::of);
          RoomValidator.of(room)
                       .isModifiableBy(issuer)
                       .ifInvalidThrow(ValidationException::of);

          sessions.remove(room);
          room.setClosed(true);
          roomRepository.save(room);
     }
}
