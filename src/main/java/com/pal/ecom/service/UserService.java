package com.pal.ecom.service;

import com.pal.ecom.api.model.LoginBody;
import com.pal.ecom.api.model.RegistrationBody;
import com.pal.ecom.exception.EmailFailureException;
import com.pal.ecom.exception.UserAlreadyExistsException;
import com.pal.ecom.exception.UserNotVerifiedException;
import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.VerificationToken;


public interface UserService {

    LocalUser registerUser (RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException;
    String loginUser(LoginBody loginBody) throws EmailFailureException, UserNotVerifiedException;
    boolean verifyUser(String token);
    VerificationToken createVerificationToken(LocalUser user);
}
