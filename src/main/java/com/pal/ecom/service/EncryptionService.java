package com.pal.ecom.service;

public interface EncryptionService {

    String encryptPassword(String password);
    Boolean verifyPassword(String password, String hash);
}
