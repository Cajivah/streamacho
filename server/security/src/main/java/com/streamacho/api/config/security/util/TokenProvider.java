package com.streamacho.api.config.security.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.streamacho.api.config.security.exception.InvalidJWTException;
import com.streamacho.api.config.security.mapper.WebSecurityMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.function.Predicate;

import static com.streamacho.api.config.security.util.SecurityConstants.ALGORITHM;
import static com.streamacho.api.config.security.util.SecurityConstants.EXPIRATION_TIME;
import static com.streamacho.api.config.security.util.SecurityConstants.SECRET_BYTES;
import static com.streamacho.api.config.security.util.SecurityConstants.TOKEN_PREFIX;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TokenProvider {

     public static final Predicate<String> isJWTTokenHeader =
          header -> header != null && header.startsWith(TOKEN_PREFIX);
     private final WebSecurityMapper webSecurityMapper;
     private MACVerifier macVerifier;
     private MACSigner macSigner;
     private JWSHeader jwsHeader;

     @PostConstruct
     private void setUp() throws JOSEException {
          macVerifier = new MACVerifier(SECRET_BYTES);
          macSigner = new MACSigner(SECRET_BYTES);
          jwsHeader = new JWSHeader(ALGORITHM);
     }

     public String createSignedJWTHeader(Authentication authentication) throws JOSEException {
          final JWTClaimsSet claimsSet = createClaimsSet(authentication);
          final SignedJWT signedJWT = claimsToSignedToken(claimsSet);
          return signedJWT.serialize();
     }

     public Authentication getAuthentication(HttpServletRequest request) {
          final String jwtToken = extractToken(request);
          return Try.of(() -> verifyAndExtractUserPrincipal(jwtToken))
               .mapTry(this::createAuthenticationToken)
               .getOrElseThrow(InvalidJWTException::ofThrowable);
     }

     private JWTClaimsSet createClaimsSet(Authentication authentication) {
          final User user = webSecurityMapper.toPrincipal(authentication);
          return new JWTClaimsSet.Builder()
               .subject(user.getUsername())
               .expirationTime(getNewTokenExpirationDate())
               .build();
     }

     private Date getNewTokenExpirationDate() {
          return new Date(System.currentTimeMillis() + EXPIRATION_TIME);
     }

     private SignedJWT claimsToSignedToken(JWTClaimsSet claimsSet) throws JOSEException {
          SignedJWT signedJWT = createSignedJWTHeader(claimsSet);
          signedJWT.sign(macSigner);
          return signedJWT;
     }

     private SignedJWT createSignedJWTHeader(JWTClaimsSet claimsSet) {
          return new SignedJWT(jwsHeader, claimsSet);
     }

     private String extractToken(HttpServletRequest request) {
          final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
          return header.substring(TOKEN_PREFIX.length());
     }

     private UserDetails verifyAndExtractUserPrincipal(String token) throws ParseException, JOSEException {
          final SignedJWT signedJWT = SignedJWT.parse(token);
          JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
          if (!isSignatureValid(signedJWT)) {
               throw new InvalidJWTException();
          }
          return webSecurityMapper.toUser(claims);
     }

     private boolean isSignatureValid(SignedJWT signedJWT) throws JOSEException {
          return signedJWT.verify(macVerifier);
     }

     private UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails user) {
          return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
     }
}
