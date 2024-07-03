package com.lm.SpringSecLogin.security.dto;

import lombok.NoArgsConstructor;                  // Lombok annotation to create a no-args constructor
import lombok.Getter;                             // Lombok annotation to create getters
import lombok.Setter;                             // Lombok annotation to create setters
import lombok.ToString;                           // Lombok annotation to create a toString method

import javax.validation.constraints.NotEmpty;     // Import used for validation of fields, checks that the annotated field is not null or empty
import javax.validation.constraints.Email;        // Import used for validation of fields, checks that the annotated field is a valid email address

/**
 * RegistrationRequest class is used to store the user's name, username, email, and password
 * during the registration process.
 * It's a Data Transfer Object (DTO) used to transfer data between software application subsystems.
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
public class RegistrationRequest
{
  @NotEmpty(message = "{Name cannot be empty}")
  private String name;

  @NotEmpty(message = "{Username cannot be empty}")
  private String username;

  @Email(message = "{Email should be valid}")
  @NotEmpty(message = "{Email cannot be empty}")
  private String email;

  @NotEmpty(message = "{Password cannot be empty}")
  private String password;
}