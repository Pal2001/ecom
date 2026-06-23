package com.pal.ecom.api.controller.product.auth;

import com.pal.ecom.api.model.LoginBody;
import com.pal.ecom.api.model.LoginResponse;
import com.pal.ecom.api.model.RegistrationBody;
import com.pal.ecom.exception.EmailFailureException;
import com.pal.ecom.exception.UserAlreadyExistsException;
import com.pal.ecom.exception.UserNotVerifiedException;
import com.pal.ecom.model.LocalUser;
import com.pal.ecom.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) throws UserAlreadyExistsException {
        LocalUser user = new LocalUser();
        try{
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        }
        catch (UserAlreadyExistsException ex){
            return ResponseEntity.status(CONFLICT).build();
        }
        catch (EmailFailureException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = null;
        try {
            jwt = userService.loginUser(loginBody);
        } catch (EmailFailureException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        } catch (UserNotVerifiedException e) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if(e.isNewEmailSent()){
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(FORBIDDEN).body(response);
        }
        if(jwt == null){
           return ResponseEntity.status(UNAUTHORIZED).build();
       }
       else{
           LoginResponse loginResponse = new LoginResponse();
           loginResponse.setJwt(jwt);
           loginResponse.setSuccess(true);
           return ResponseEntity.ok(loginResponse);
       }
    }

    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser localUser){
        return localUser;
    }

    @PostMapping("/verify")
    public ResponseEntity verifyUser(@RequestParam String token){
        if(userService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(CONFLICT).build();
        }
    }
}
