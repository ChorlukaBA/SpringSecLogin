package com.lm.SpringSecLogin.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.lm.SpringSecLogin.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}