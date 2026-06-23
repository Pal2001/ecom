package com.pal.ecom.api.model;

import lombok.Data;

@Data
public class LoginResponse {


    private String jwt;
    private boolean success;
    private String failureReason;
}
