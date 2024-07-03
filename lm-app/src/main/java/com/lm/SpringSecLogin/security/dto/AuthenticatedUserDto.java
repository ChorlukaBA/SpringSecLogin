package com.lm.SpringSecLogin.security.dto;

import lombok.NoArgsConstructor;                // lombok annotation to automatically generate a constructor with no arguments
import lombok.Getter;                           // lombok annotation to automatically generate getters
import lombok.Setter;                           // lombok annotation to automatically generate setters
import com.lm.SpringSecLogin.model.UserRole;    // import the UserRole enum model (JPA entity)

/*
    * This class is a Data Transfer Object (DTO, used to transfer data between software application subsystems) for the authenticated user.
    * It contains the authenticated user's name, username, password, and user role.
 */

@NoArgsConstructor
@Getter
@Setter
public class AuthenticatedUserDto
{
  private String name;
  private String username;
  private String password;
  private UserRole userRole;
}