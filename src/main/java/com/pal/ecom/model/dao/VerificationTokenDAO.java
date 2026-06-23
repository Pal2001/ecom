package com.pal.ecom.model.dao;

import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationTokenDAO extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByToken(String token);
    void deleteByUser(LocalUser user);
    List<VerificationToken> findByUser_IdOrderByIdDesc(Long id);
}
