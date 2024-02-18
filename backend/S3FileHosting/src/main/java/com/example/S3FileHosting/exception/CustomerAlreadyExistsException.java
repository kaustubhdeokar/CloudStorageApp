package com.example.S3FileHosting.exception;

import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistsException extends RuntimeException {

    private String message;

    public CustomerAlreadyExistsException() {
    }

    public CustomerAlreadyExistsException(String msg) {
        super(msg);
        this.message = msg;
    }

    public CustomerAlreadyExistsException(HttpStatus status, String msg) {
        super(msg);
        this.message = msg;
    }
}