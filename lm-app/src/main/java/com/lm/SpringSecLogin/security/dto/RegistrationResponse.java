package com.lm.SpringSecLogin.security.dto;

import lombok.AllArgsConstructor;               // Lombok annotation to create a constructor with all arguments
import lombok.Getter;                           // Lombok annotation to create getters
import lombok.Setter;                           // Lombok annotation to create setters
import lombok.NoArgsConstructor;                // Lombok annotation to create a no-args constructor

/**
 * RegistrationResponse class is used to store the message generated during the registration process.
 * It's a Data Transfer Object (DTO) used to transfer data between software application subsystems.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse
{
    private String message;
}