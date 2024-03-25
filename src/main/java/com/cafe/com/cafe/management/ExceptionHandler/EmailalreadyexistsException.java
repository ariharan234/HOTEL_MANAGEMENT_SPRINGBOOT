package com.cafe.com.cafe.management.ExceptionHandler;

public class EmailalreadyexistsException extends RuntimeException{
    public EmailalreadyexistsException(String message){
        super(message);
    }
}
