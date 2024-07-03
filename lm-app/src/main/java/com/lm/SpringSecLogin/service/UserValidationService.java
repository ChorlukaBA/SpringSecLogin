package com.lm.SpringSecLogin.service;

import com.lm.SpringSecLogin.exceptions.RegistrationException;          // Import of RegistrationException, a custom exception for registration errors
import com.lm.SpringSecLogin.repository.UserRepository;                 // Import of UserRepository, for user management (check if user exists)
import com.lm.SpringSecLogin.security.dto.RegistrationRequest;          // Import of RegistrationRequest dto, for registration request data management
import lombok.RequiredArgsConstructor;                                  // Import of RequiredArgsConstructor, for automatic constructor generation with Lombok
import lombok.extern.slf4j.Slf4j;                                       // Import of slf4j logger for logging
import org.springframework.stereotype.Service;                          // Import of Service, for service class

/*
    * UserValidationService is a service class that provides methods for user validation.
    * It checks if the email and username already exist in the database and throws an exception if they do. This is done to prevent duplicate users.
    * Checks are private methods that are called from the UserServiceImpl class in the registration phase.
    * The class is annotated with @Service, so Spring will automatically detect it as a Spring bean.
    * The class is annotated with @RequiredArgsConstructor, so Lombok will generate a constructor with all class fields.
    * The class uses slf4j logger for logging.
    *
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserValidationService
{
    private final UserRepository userRepository;

    public void validateUser(RegistrationRequest registrationRequest)
    {
        final String email = registrationRequest.getEmail();
        final String username = registrationRequest.getUsername();

        checkEmail(email);
        checkUsername(username);
    }

    private void checkUsername(String username)
    {
        final boolean existsByUsername = userRepository.existsByUsername(username);

        if (existsByUsername)
        {
            log.warn("Username already exists: {}", username);
            final String existsUsername = "Username already exists";
            throw new RegistrationException(existsUsername);
        }
    }

    private void checkEmail(String email)
    {
        final boolean existsByEmail = userRepository.existsByEmail(email);

        if (existsByEmail)
        {
            log.warn("Email already exists: {}", email);
            final String existsEmail = "Email already exists";
            throw new RegistrationException(existsEmail);
        }
    }
}