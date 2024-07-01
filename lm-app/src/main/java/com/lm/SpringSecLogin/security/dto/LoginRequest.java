package com.lm.SpringSecLogin.security.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

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