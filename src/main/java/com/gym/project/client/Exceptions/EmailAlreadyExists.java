package com.gym.project.client.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailAlreadyExists extends RuntimeException{
    private String message;

    public EmailAlreadyExists(String message) {
        super(message);
    }
}
