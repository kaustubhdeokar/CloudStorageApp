package com.example.S3FileHosting.exception;

public class CustomerAlreadyExistsException extends RuntimeException {

    private String message;

    public CustomerAlreadyExistsException() {}

    public CustomerAlreadyExistsException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}