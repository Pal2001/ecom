package com.pal.ecom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@Entity
@Table(name="verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
//    @Lob
    @Column(name="token", nullable = false, unique = true)
    private String token;
    @Column(name="creating_timestamp", nullable = false)
    private Timestamp createdTimeStamp;

    @ManyToOne(optional = false)
    @JoinColumn(name="user_id", nullable = false)
    private LocalUser user;
}
