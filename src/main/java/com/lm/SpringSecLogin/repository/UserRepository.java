package com.lm.SpringSecLogin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lm.SpringSecLogin.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
  #Optional because the user may not exist in the database.

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}