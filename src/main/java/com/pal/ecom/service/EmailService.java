package com.pal.ecom.service;

import com.pal.ecom.exception.EmailFailureException;
import com.pal.ecom.model.VerificationToken;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    SimpleMailMessage makeMailMessage();

    void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException;
}
