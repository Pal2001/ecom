package com.pal.ecom.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pal.ecom.model.LocalUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService{

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt-issuer}")
    private String issuer;

    @Value("${expiryInSeconds}")
    private int expiryInSeconds;

    private static final String USERNAME_KEY = "USERNAME";
    private static final String EMAIL_KEY = "EMAIL";
    private Algorithm algorithm;

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey.getBytes(StandardCharsets.UTF_8));
    }
    @Override
    public String generateJwt(LocalUser user){
        return JWT.create()
                .withClaim(USERNAME_KEY, user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    @Override
    public String generateVerificationJwt(LocalUser user){
        return JWT.create()
                .withClaim(EMAIL_KEY, user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * expiryInSeconds)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    @Override
    public String getUserName(String token){
        return JWT.decode(token).getClaim(USERNAME_KEY).asString();
    }

}
