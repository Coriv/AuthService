package com.microservices.authservice.globalExceptionHandler;

import com.microservices.authservice.exception.UserIdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<Object> authIdNotFoundExceptionHandler(UserIdNotFoundException e) {
        return new ResponseEntity<>("Authentication with given ID not found", HttpStatus.NOT_FOUND);
    }
}
