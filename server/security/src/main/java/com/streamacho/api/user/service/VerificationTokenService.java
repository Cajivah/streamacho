package com.streamacho.api.user.service;

import com.streamacho.api.user.exception.VerificationException;
import com.streamacho.api.user.model.dto.ResetPasswordDTO;
import com.streamacho.api.user.model.dto.UserDetailsDTO;
import com.streamacho.api.user.model.entity.UserCredentials;
import com.streamacho.api.user.model.entity.VerificationToken;
import com.streamacho.api.user.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.streamacho.api.user.util.VerificationTokenUtils.getEarliestValidCreationDate;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationTokenService {

     private final UserCredentialsService userCredentialsService;
     private final VerificationTokenRepository verificationTokenRepository;

     public VerificationToken createNewToken(UserCredentials user) {
          final VerificationToken token = buildToken(user);
          return verificationTokenRepository.save(token);
     }

     private VerificationToken buildToken(UserCredentials userCredentials) {
          final String uuid = UUID.randomUUID().toString();
          return VerificationToken.builder()
                                  .token(uuid)
                                  .user(userCredentials)
                                  .build();
     }

     public VerificationToken createNewToken(UserDetailsDTO user) {
          final UserCredentials userCredentials =
                  userCredentialsService.findByUsername(user.getUsername());
          final VerificationToken token = buildToken(userCredentials);
          return verificationTokenRepository.save(token);
     }

     public void verifyUser(String token) {
          final VerificationToken verificationToken = findVerificationByToken(token);
          persistVerification(verificationToken);
     }

     private VerificationToken findVerificationByToken(String token) {
          return verificationTokenRepository.findOneByTokenAndCreatedAfter(
               token,
               getEarliestValidCreationDate())
               .orElseThrow(VerificationException::of);
     }

     private void persistVerification(VerificationToken verificationToken) {
          userCredentialsService.verifyUser(verificationToken.getUser());
          verificationTokenRepository.delete(verificationToken);
     }

     public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
          VerificationToken verificationToken = findVerificationByToken(resetPasswordDTO.getToken());
          UserCredentials user = verificationToken.getUser();
          userCredentialsService.resetPassword(resetPasswordDTO, user);
          verificationTokenRepository.delete(verificationToken);
     }
}
