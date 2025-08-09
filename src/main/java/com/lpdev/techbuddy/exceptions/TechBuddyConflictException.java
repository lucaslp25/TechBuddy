package com.lpdev.techbuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TechBuddyConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TechBuddyConflictException(String message) {
        super(message);
    }
}
