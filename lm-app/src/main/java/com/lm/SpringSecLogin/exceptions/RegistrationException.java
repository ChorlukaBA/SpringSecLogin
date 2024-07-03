package com.lm.SpringSecLogin.exceptions;

import lombok.RequiredArgsConstructor;  // Lombok annotation to automatically generate a constructor with required fields
import lombok.Getter;                   // Lombok annotation to automatically generate a getter method for each field

/**
 * This class is a custom exception that is thrown when a registration error occurs.
 * It extends the RuntimeException class and has a single field for the error message.
 */

@Getter
@RequiredArgsConstructor
public class RegistrationException extends RuntimeException
{
    private final String errorMessage;
}