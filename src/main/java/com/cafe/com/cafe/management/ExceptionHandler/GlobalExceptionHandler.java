package com.cafe.com.cafe.management.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailalreadyexistsException.class)
    public ResponseEntity<ErrorDetails> emailexists(EmailalreadyexistsException ex){
        ErrorDetails er=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(),"Email_Already_Exsits");
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> resourcenotfound(Exception ex){
        ErrorDetails er=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(),"Internal_Server_Error_Exception");
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(com.cafe.com.cafe.management.ExceptionHandler.AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> accessdenied(AccessDeniedException ex){
        ErrorDetails er=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(),"Access denied: User is not authorized to perform this operation");
        return new ResponseEntity<>(er, HttpStatus.UNAUTHORIZED);
    }
}
