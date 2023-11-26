package com.intuit.complaintservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ComplaintException extends RuntimeException{

    private final HttpStatus status;

    public ComplaintException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
