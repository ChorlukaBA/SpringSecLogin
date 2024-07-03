package com.lm.SpringSecLogin.model;

import lombok.*;            // lombok annotations

import javax.persistence.*; // JPA annotations

/*
    This class is the User entity (JPA) that will be mapped to the database table USERS.
    It has the following fields:
    - id: the primary key of the entity
    - name: the name of the user
    - username: the username of the user
    - email: the email of the user
    - password: the password of the user
    - userRole: the role of the user

    We use the lombok annotations to generate the getters, setters, constructors, and builder methods.
 */

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