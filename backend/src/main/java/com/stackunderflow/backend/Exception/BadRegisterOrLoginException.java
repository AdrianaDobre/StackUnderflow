package com.stackunderflow.backend.Exception;

public class BadRegisterOrLoginException extends RuntimeException{
    public BadRegisterOrLoginException(String message) {
        super(message);
    }
}
