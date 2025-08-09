package com.lpdev.techbuddy.exceptions.handler;

import com.lpdev.techbuddy.exceptions.TechBuddyConflictException;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalHandlerExeption {

    @ExceptionHandler(TechBuddyUnprocessableException.class)
    public ResponseEntity<StandardError> TechBuddyUnprocessableException(TechBuddyUnprocessableException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String name = "Tech Buddy Unprocessable Exception";
        String message = e.getMessage();
        String path = request.getRequestURI();

        StandardError standardError = new StandardError(name, message, status, Instant.now(), path);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(TechBuddyConflictException.class)
    public ResponseEntity<StandardError> TechBuddyConflitException(TechBuddyConflictException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        String name = "Tech Buddy Conflict Exception";
        String message = e.getMessage();
        String path = request.getRequestURI();

        StandardError standardError = new StandardError(name, message, status, Instant.now(), path);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(TechBuddySecurityException.class)
    public ResponseEntity<StandardError> TechBuddySecurityException(TechBuddySecurityException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String name = "Tech Buddy Unauthorized Exception";
        String message = e.getMessage();
        String path = request.getRequestURI();

        StandardError standardError = new StandardError(name, message, status, Instant.now(), path);

        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(TechBuddyNotFoundException.class)
    public ResponseEntity<StandardError> TechBuddyNotFoundException(TechBuddyNotFoundException e, HttpServletRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String name = "Tech Buddy Not found Exception";
        String message = e.getMessage();
        String path = request.getRequestURI();

        StandardError standardError = new StandardError(name, message, status, Instant.now(), path);

        return ResponseEntity.status(status).body(standardError);
    }

}
