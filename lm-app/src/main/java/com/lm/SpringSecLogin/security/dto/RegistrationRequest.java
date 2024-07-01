package com.lm.SpringSecLogin.security.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Email;

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