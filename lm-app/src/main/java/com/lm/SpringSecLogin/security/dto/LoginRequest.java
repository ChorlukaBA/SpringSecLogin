package com.lm.SpringSecLogin.security.dto;

import lombok.NoArgsConstructor;        // Lombok annotation to create a no-args constructor
import lombok.Getter;                   // Lombok annotation to create getters
import lombok.Setter;                   // Lombok annotation to create setters

import javax.validation.constraints.NotEmpty; // Import used for validation of fields

/**
 * LoginRequest class is used to store the username and password entered by the user
 * during the login process.
 * It's a Data Transfer Object (DTO) used to transfer data between software application subsystems.
 */

@NoArgsConstructor
@Getter
@Setter
public class LoginRequest
{
  @NotEmpty(message = "{Username cannot be empty}")
  private String username;

  @NotEmpty(message = "{Password cannot be empty}")
  private String password;
}