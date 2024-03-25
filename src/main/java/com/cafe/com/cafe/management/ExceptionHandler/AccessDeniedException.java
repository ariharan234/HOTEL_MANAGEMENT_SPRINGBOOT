package com.cafe.com.cafe.management.ExceptionHandler;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String message) {
        super(message);
    }
}
