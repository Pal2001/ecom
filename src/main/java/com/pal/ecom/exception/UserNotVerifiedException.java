package com.pal.ecom.exception;

import lombok.Getter;


@Getter
public class UserNotVerifiedException extends Exception{

    private boolean newEmailSent;

    public UserNotVerifiedException(boolean newEmailSent) {
        this.newEmailSent = newEmailSent;
    }
}
