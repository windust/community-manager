package com.spinningnoodle.communitymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not Logged In")
public class NotLoggedInException extends Exception {
    public NotLoggedInException(){
        super("Please login in order to access this page");
    }
}
