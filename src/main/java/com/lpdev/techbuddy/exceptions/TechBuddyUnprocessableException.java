package com.lpdev.techbuddy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TechBuddyUnprocessableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TechBuddyUnprocessableException(String message) {
        super(message);
    }
}
