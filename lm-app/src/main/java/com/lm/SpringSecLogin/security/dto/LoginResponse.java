package com.lm.SpringSecLogin.security.dto;

import lombok.AllArgsConstructor;           // Lombok annotation to create a constructor with all arguments
import lombok.Getter;                       // Lombok annotation to create getters
import lombok.Setter;                       // Lombok annotation to create setters

/**
 * LoginResponse class is used to store the JWT token generated during the login process.
 * It's a Data Transfer Object (DTO) used to transfer data between software application subsystems.
 */

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse
{
    private String token;
}