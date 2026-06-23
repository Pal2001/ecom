package com.pal.ecom.service;

import com.pal.ecom.api.model.LoginBody;
import com.pal.ecom.api.model.RegistrationBody;
import com.pal.ecom.exception.EmailFailureException;
import com.pal.ecom.exception.UserAlreadyExistsException;
import com.pal.ecom.exception.UserNotVerifiedException;
import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.VerificationToken;
import com.pal.ecom.model.dao.LocalUserDAO;
import com.pal.ecom.model.dao.VerificationTokenDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    EmailService emailService;
    @Autowired
    private EncryptionService encryptionService;
    @Autowired
    private LocalUserDAO localUserDAO;
    @Autowired
    private JWTService jwtService;
    @Autowired
    VerificationTokenDAO verificationTokenDAO;
    @Override
    public LocalUser registerUser(RegistrationBody registrationBody) throws UserAlreadyExistsException, EmailFailureException {
        if(localUserDAO.findByUsernameIgnoreCase(registrationBody.getUserName()).isPresent()
                || localUserDAO.findByEmailIgnoreCase(registrationBody.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        LocalUser user = new LocalUser();
        user.setEmail(registrationBody.getEmail());
        user.setUsername(registrationBody.getUserName());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());
        user.setPassword(encryptionService.encryptPassword(registrationBody.getPassword()));
        LocalUser savedUser = localUserDAO.save(user);
        VerificationToken verificationToken = createVerificationToken(savedUser);
        verificationTokenDAO.save(verificationToken);
        emailService.sendVerificationEmail(verificationToken);
        return savedUser;
    }

    @Override
    public String loginUser(LoginBody loginBody) throws EmailFailureException, UserNotVerifiedException {
        Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(loginBody.getUserName());
        if(opUser.isPresent()){
            LocalUser user = opUser.get();
            if(encryptionService.verifyPassword(loginBody.getPassword(), user.getPassword())){
                if(user.isEmailVerified()) {
                    return jwtService.generateJwt(user);
                }else{
                    List<VerificationToken> verificationTokens = user.getVerificationTokens();
                    boolean resend = verificationTokens.size()==0 ||
                            verificationTokens.get(0).getCreatedTimeStamp()
                                    .before(new Timestamp(System.currentTimeMillis()- (60 * 60 * 1000)));
                    if(resend){
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }

    @Override
    public VerificationToken createVerificationToken(LocalUser user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateVerificationJwt(user));
        verificationToken.setCreatedTimeStamp(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerificationTokens().add(verificationToken);
        return verificationToken;
    }
    @Transactional
    @Override
    public boolean verifyUser(String token){
        Optional<VerificationToken> opToken = verificationTokenDAO.findByToken(token);
        if(opToken.isPresent()){
            VerificationToken verificationToken = opToken.get();
            LocalUser user = verificationToken.getUser();
            if(!user.isEmailVerified()){
                user.setEmailVerified(true);
                localUserDAO.save(user);
                verificationTokenDAO.deleteByUser(user);
                return true;
            }
        }
        return false;
    }
}
