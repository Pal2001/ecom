 package com.pal.ecom.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetup;
import com.pal.ecom.api.model.LoginBody;
import com.pal.ecom.api.model.RegistrationBody;
import com.pal.ecom.exception.EmailFailureException;
import com.pal.ecom.exception.UserAlreadyExistsException;
import com.pal.ecom.exception.UserNotVerifiedException;
import com.pal.ecom.model.VerificationToken;
import com.pal.ecom.model.dao.VerificationTokenDAO;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


 @SpringBootTest
 @AutoConfigureMockMvc
public class UserServiceTest {

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(new ServerSetup(3025, null, "smtp"))
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);

    @Autowired
    private VerificationTokenDAO verificationTokenDAO;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        RegistrationBody body = new RegistrationBody();
        body.setUserName("UserA");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MySecretPassword@123");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Username should already be in use");
        body.setUserName("UserServiceTest$testRegisterUser");
        body.setEmail("UserA@junit.com");
        Assertions.assertThrows(UserAlreadyExistsException.class,
                () -> userService.registerUser(body), "Email should already be in use");
        body.setEmail("UserServiceTest$testRegisterUser@junit.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(body), "User should register successfully.");
        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());
    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFailureException {
        LoginBody body = new LoginBody();
        body.setUserName("UserA-NotExists");
        body.setPassword("PasswordA@123-BadPassword");
        Assertions.assertNull(userService.loginUser(body), "The user should not exists.");
        body.setUserName("UserA");
        Assertions.assertNull(userService.loginUser(body), "The password should be incorrect.");
        body.setPassword("PasswordA@123");
        Assertions.assertNotNull(userService.loginUser(body), "The user should login successfully.");
        body.setUserName("UserB");
        body.setPassword("PasswordB@123");
        try{
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertTrue(ex.isNewEmailSent(), "Email verification should sent");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
        try{
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            Assertions.assertFalse(ex.isNewEmailSent(), "Email verification should not be resent");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }
    }

    @Test
    @Transactional
    public void testVerifyUser() throws EmailFailureException, UserNotVerifiedException {
        Assertions.assertFalse(userService.verifyUser("Bad Token"), "Token that is bad or does not exist return false.");
        LoginBody body = new LoginBody();
        body.setUserName("UserB");
        body.setPassword("PasswordB@123");
        try{
            userService.loginUser(body);
            Assertions.assertTrue(false, "User should not have email verified.");
        } catch (UserNotVerifiedException ex) {
            List<VerificationToken> tokens = verificationTokenDAO.findByUser_IdOrderByIdDesc(2L);
            String token = tokens.get(0).getToken();
            Assertions.assertTrue(userService.verifyUser(token));
            Assertions.assertNotNull(body, "The user should now be verified.");
        }
    }
}
