package com.streamacho.api.config.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.streamacho.api.config.security.exception.InvalidJWTException;
import com.streamacho.api.config.security.model.UserPrincipal;
import org.springframework.security.core.userdetails.User;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Predicate;

import static com.streamacho.api.config.security.mapper.SecurityMapper.toUserPrincipal;
import static com.streamacho.api.config.security.util.SecurityConstants.*;

public class TokenUtils {

     public static final Predicate<String> isJWTTokenHeader =
          header -> header != null && header.startsWith(TOKEN_PREFIX);

     public static SignedJWT createSignedJWT(User principal) throws JOSEException {

          final JWTClaimsSet claimsSet = createClaimsSet(principal);
          return claimsToSignedToken(claimsSet);
     }

     public static UserPrincipal verifyAndExtractUserPrincipal(String token) throws ParseException, JOSEException {
          final SignedJWT signedJWT = SignedJWT.parse(token);
          JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
          if (!isSignatureValid(signedJWT)) {
               throw new InvalidJWTException();
          }
          return toUserPrincipal(claims);
     }

     private static boolean isSignatureValid(SignedJWT signedJWT) throws JOSEException {
          final MACVerifier macVerifier = createMACVerifier();
          return signedJWT.verify(macVerifier);
     }

     private static MACVerifier createMACVerifier() throws JOSEException {
          return new MACVerifier(SECRET_BYTES);
     }

     private static JWTClaimsSet createClaimsSet(User principal) {
          return new JWTClaimsSet.Builder()
               .subject(principal.getUsername())
               .expirationTime(getNewTokenExpirationDate())
               .build();
     }

     private static Date getNewTokenExpirationDate() {
          return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
     }

     private static SignedJWT claimsToSignedToken(JWTClaimsSet claimsSet) throws JOSEException {

          final JWSSigner signer = createMacSigner();
          SignedJWT signedJWT = createSignedJWT(claimsSet);
          signedJWT.sign(signer);
          return signedJWT;
     }

     private static SignedJWT createSignedJWT(JWTClaimsSet claimsSet) {
          return new SignedJWT(getTokenHeader(), claimsSet);
     }

     private static JWSHeader getTokenHeader() {
          return new JWSHeader(ALGORITHM);
     }

     private static MACSigner createMacSigner() throws KeyLengthException {
          return new MACSigner(SECRET_BYTES);
     }
}
