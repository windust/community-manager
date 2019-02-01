package com.spinningnoodle.communitymanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not Logged In")
public class InvalidUserException extends Exception {
    public InvalidUserException(){
        super("Please login or provide a valid token");
    }
}
