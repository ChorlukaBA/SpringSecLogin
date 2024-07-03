package com.lm.SpringSecLogin.exceptions;

import lombok.AllArgsConstructor;                   // Lombok annotation to automatically generate constructor with all arguments
import lombok.Getter;                               // Lombok annotation to automatically generate getters
import lombok.NoArgsConstructor;                    // Lombok annotation to automatically generate constructor with no arguments
import lombok.Setter;                               // Lombok annotation to automatically generate setters
import org.springframework.http.HttpStatus;         // Spring annotation to handle HTTP status codes in the response

import java.time.LocalDateTime;                     // Java class to represent date and time

/*
    * This class is used to create a custom response for exceptions, made of a message, a status code and a timestamp
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse
{
    private String message;
    private HttpStatus status;
    private LocalDateTime time;
}