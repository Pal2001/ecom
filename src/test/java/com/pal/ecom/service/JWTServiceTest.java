package com.pal.ecom.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.dao.LocalUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTServiceTest {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LocalUserDAO localUserDAO;

    @Test
    public void testVerifcationTokenNotUsableForLogin(){
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateVerificationJwt(user);
        Assertions.assertNull(jwtService.getUserName(token), "Verification token should not contain username.");
    }

    @Test
    public void testAuthTokenReturnsUsername(){
        LocalUser user = localUserDAO.findByUsernameIgnoreCase("UserA").get();
        String token = jwtService.generateJwt(user);
        Assertions.assertEquals(user.getUsername(), jwtService.getUserName(token), "Token for auth should contain users username");
    }

    @Test
    public void testJWTNotGeneratedByUs() throws UnsupportedEncodingException {
        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256("NotTheRealSecret"));
        Assertions.assertThrows(SignatureVerificationException.class, () -> jwtService.getUserName(token));
    }

    @Test
    public void testJWTCorrectlySignedNoIssuer() throws UnsupportedEncodingException {
        String token = JWT.create().withClaim("USERNAME", "UserA").sign(Algorithm.HMAC256(algorithmKey));
        Assertions.assertThrows(InvalidClaimException.class, () -> jwtService.getUserName(token));

    }
}
