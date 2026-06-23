package com.pal.ecom.service;

import com.pal.ecom.exception.EmailFailureException;
import com.pal.ecom.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Value("${email.from}")
    private String fromAddress;
    @Value("${app.frontend.url}")
    private String url;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public SimpleMailMessage makeMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    @Override
    public void sendVerificationEmail(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage message = makeMailMessage();
        message.setTo(verificationToken.getUser().getEmail());
        message.setSubject("Verify your email to active your account");
        message.setText("Please follow the link below to active your account.\n" + url + "/auth/verify?token=" + verificationToken.getToken());
        try {
            javaMailSender.send(message);
        }
        catch (MailException ex){
            throw new EmailFailureException();
        }
    }


}
