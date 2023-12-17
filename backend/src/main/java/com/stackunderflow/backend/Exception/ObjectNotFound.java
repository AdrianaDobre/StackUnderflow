package com.stackunderflow.backend.Exception;

public class ObjectNotFound extends RuntimeException{
    public ObjectNotFound(String message) {
        super(message);
    }
}
