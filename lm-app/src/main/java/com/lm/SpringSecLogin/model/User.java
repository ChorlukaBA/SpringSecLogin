package com.lm.SpringSecLogin.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
  private String username;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private UserRole userRole;
}