package com.lm.SpringSecLogin.exceptions;

import com.lm.SpringSecLogin.controller.LoginController;                                   // Import of our controller class. Needed to specify the base package for the @RestControllerAdvice annotation.
import org.springframework.http.HttpStatus;                                                // Import of HttpStatus class. This allows us to specify the status of the response.
import org.springframework.http.ResponseEntity;                                            // Import of ResponseEntity class. This allows us to return a response entity.
import org.springframework.security.authentication.BadCredentialsException;                // Import of BadCredentialsException class. This is the exception that is thrown when the credentials are invalid.
import org.springframework.web.bind.annotation.ExceptionHandler;                           // Import of ExceptionHandler annotation. This allows us to handle exceptions in our controller advice.
import org.springframework.web.bind.annotation.RestControllerAdvice;                       // Import of RestControllerAdvice annotation. Needed to handle exceptions in our controller advice.

import java.time.LocalDateTime;                                                   // Import of LocalDateTime class. This allows us to get the current date and time.

/**
 * This class is a controller advice that handles exceptions thrown by the LoginController class.
 * It handles the BadCredentialsException exception and returns a response with the exception message and status.
 */

@RestControllerAdvice(basePackageClasses = LoginController.class)                 // Here we specify the base package of the controller that we want to handle exceptions for.
public class LoginControllerAdvice
{
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionResponse> handleBadCredentialsException(BadCredentialsException exception)
    {
        final ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());   // Create a new ApiExceptionResponse object with the exception message, status and current date and time.
        return ResponseEntity.status(response.getStatus()).body(response);       // Return a response entity with the status and body of the ApiExceptionResponse object.
    }
}