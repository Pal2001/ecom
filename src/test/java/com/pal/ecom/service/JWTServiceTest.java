package com.pal.ecom.service;

import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.dao.LocalUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class JWTServiceTest {

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
}
