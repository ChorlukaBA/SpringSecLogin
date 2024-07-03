package com.lm.SpringSecLogin.exceptions;

import lombok.extern.slf4j.Slf4j;                                               // Lombok annotation to autogenerate an SLF4J logger
import org.springframework.context.support.DefaultMessageSourceResolvable;      // Spring class to handle error messages
import org.springframework.http.HttpStatus;                                     // Spring class to handle HTTP status codes
import org.springframework.http.ResponseEntity;                                 // Spring class to handle HTTP responses
import org.springframework.validation.FieldError;                               // Spring class to handle field errors, used in validation
import org.springframework.web.bind.MethodArgumentNotValidException;            // Spring class to handle method arguments that are not valid
import org.springframework.web.bind.annotation.ExceptionHandler;                // Spring annotation to handle exceptions
import org.springframework.web.bind.annotation.RestControllerAdvice;            // Spring annotation that allows to handle exceptions in a global way

import java.time.LocalDateTime;                                               // Java class to handle date and time
import java.util.List;                                                        // Java class to handle lists
import java.util.stream.Collectors;                                           // Class used to manipulate streams of data

/*
    * This class is used to handle exceptions related to validation errors in the application.
    * It uses the @RestControllerAdvice annotation to handle exceptions in a global way.
    * The @ExceptionHandler annotation is used to handle exceptions of the type MethodArgumentNotValidException, which is thrown when a method argument fails validation.
    *
    * In the handleMethodArgumentNotValidException method, the field errors are extracted from the exception and transformed into a list of strings.
    * The response is then built using the ValidationErrorResponse class, which contains the HTTP status, the current date and time, and the list of errors.
    * We log the validation errors and the parameters that caused the validation errors.
    * The response is then returned with the appropriate status code.
 */

@Slf4j
@RestControllerAdvice
public class ValidationAdvice
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception)
    {
        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final List<String> errorList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        final ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errorList);
        log.warn("Validation errors: {}, Parameters: {}", errorList, exception.getBindingResult().getTarget());

        return ResponseEntity.status(validationErrorResponse.getStatus()).body(validationErrorResponse);
    }
}