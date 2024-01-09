package com.stackunderflow.backend.Exception;

public class NoEditAcceptedException extends RuntimeException{
    public NoEditAcceptedException(String message) {
        super(message);
    }
}
