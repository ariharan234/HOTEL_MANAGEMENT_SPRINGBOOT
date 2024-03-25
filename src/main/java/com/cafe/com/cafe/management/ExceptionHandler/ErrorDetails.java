package com.cafe.com.cafe.management.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String status;
    private String error;


}
