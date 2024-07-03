package com.lm.SpringSecLogin.repository;


import org.springframework.data.jpa.repository.JpaRepository;       // Import of JpaRepository, which provides methods for CRUD operations
import com.lm.SpringSecLogin.model.User;                            // User model class (Entity, POJO in JPA terminology)

/*
    * This interface extends JpaRepository, which provides CRUD methods for User entity.
    * It also provides custom methods to find a user by username, and to check if a user exists by username or email.
 */


public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}