package com.lm.SpringSecLogin.configuration;

import org.springframework.context.annotation.Bean;                         // Import of Bean class. We use Bean class to define a method that returns an object that should be registered as a bean in the Spring application context.
import org.springframework.context.annotation.Configuration;                // Import of Configuration class. We use Configuration class to define a class as a configuration class in Spring application context.
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;    // Import of BCryptPasswordEncoder class. We use BCryptPasswordEncoder class to encode passwords using BCrypt hashing function.

/*
 * PasswordEncoderConfiguration class allows us to reconfigure our password encoder on the fly.
 */


@Configuration
public class PasswordEncoderConfiguration
{

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();             // Returns an object of BCryptPasswordEncoder class. We use BCryptPasswordEncoder class to encode passwords using BCrypt hashing function.
    }
}