package com.lm.SpringSecLogin.security.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.lm.SpringSecLogin.model.UserRole;

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