package com.pal.ecom.service;

import com.pal.ecom.model.LocalUser;

public interface JWTService {

    String generateJwt(LocalUser user);
    String generateVerificationJwt(LocalUser user);
    String getUserName(String token);
    String generateResetPasswordJwt(LocalUser user);
    String getResetPasswordEmail(String token);
}
