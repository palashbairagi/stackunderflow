package com.cp.stackunderflow.security;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class TokenHandler {

    private final String ISSUER = "PNC";
    private final String AUDIENCE = "customers";
    private final String SUBJECT = "test";
    private Set<String> tokens;

    @Value("${jwt.secret}")
    String secretKey;

    public TokenHandler() {
        tokens = new HashSet<>();
    }

    public String createToken(int id) {

        Signer signer = HMACSigner.newSHA256Signer(secretKey);
        JWT jwt = new JWT().setIssuer(ISSUER)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setSubject(SUBJECT)
                .setAudience(AUDIENCE)
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60))
                .addClaim("Id", id);

        String encodedJWT = "abc" + JWT.getEncoder().encode(jwt, signer);

        tokens.add(encodedJWT);

        return encodedJWT;
    }

    public boolean validate(String token) {

        if(!tokens.contains(token)) {
            return false;
        }

        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        JWT jwt = JWT.getDecoder().decode(token.substring(3), verifier);

        if(jwt.isExpired()) {
            return false;
        }

         if(jwt.subject.equals(SUBJECT)) {
            return true;
        }

        return false;
    }

    public boolean validate(String token, int id) {

        if(!tokens.contains(token)) {
            return false;
        }

        Verifier verifier = HMACVerifier.newVerifier(secretKey);
        JWT jwt = JWT.getDecoder().decode(token.substring(3), verifier);

        if(jwt.isExpired()) {
            return false;
        }

        if(jwt.getInteger("Id").equals(id)) {
            return true;
        }

        return false;
    }

    public void revokeToken(String token) {
        tokens.remove(token);
    }

    public int getIdFromToken(String token) {

        if(validate(token)) {
            Verifier verifier = HMACVerifier.newVerifier(secretKey);
            JWT jwt = JWT.getDecoder().decode(token.substring(3), verifier);
            return jwt.getInteger("Id");
        }

        return 0;
    }
}

