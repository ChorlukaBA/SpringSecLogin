package com.lm.SpringSecLogin.security.service;

import com.lm.SpringSecLogin.model.UserRole;                                      // Here we import the UserRole, an enum for the user roles
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;                    // Here we import the AuthenticatedUserDto, our DTO class for the authenticated user
import lombok.RequiredArgsConstructor;                                               // Lombok annotation that creates a constructor with all the required arguments
import lombok.extern.slf4j.Slf4j;                                                    // Lombok annotation that creates a logger
import org.springframework.security.core.authority.SimpleGrantedAuthority;          // Import for SimpleGrantedAuthority, a simple implementation of the GrantedAuthority interface
import org.springframework.security.core.userdetails.User;                          // Import for User, a core interface which models core user information
import org.springframework.security.core.userdetails.UserDetails;                   // Import for UserDetails, a core interface which loads user-specific data
import org.springframework.security.core.userdetails.UserDetailsService;            // Import for UserDetailsService, an interface for user details service
import org.springframework.security.core.userdetails.UsernameNotFoundException;     // Import for UsernameNotFoundException, an exception thrown when a user is not found
import org.springframework.stereotype.Service;                                      // Import for Service, an annotation to indicate that the class is a service

import java.util.Collections;                             // Import for Collections, a utility class to operate on collections
import java.util.Objects;                                 // Import for Objects, a utility class to operate on objects

/*
    * UserDetailsServiceImpl is a service class that provides the logic to load the user by username.
    * It uses the UserService to find the authenticated user by username.
    * It returns a UserDetails object with the authenticated user details.
    * It throws a UsernameNotFoundException if the user is not found.
 */


@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final String USERNAME_OR_PASSWORD_INVALID = "Invalid username or password";

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        final AuthenticatedUserDto authenticatedUser = userService.findAuthenticatedUserByUsername(username);           // Here we find the authenticated user by username

        // If the authenticated user is null, throw a UsernameNotFoundException
        if (Objects.isNull(authenticatedUser))
        {
            throw new UsernameNotFoundException(USERNAME_OR_PASSWORD_INVALID);
        }

        // If the authenticated user is not null, return a new UserDetails object with the authenticated user details
        final String authenticatedUsername = authenticatedUser.getUsername();
        final String authenticatedPassword = authenticatedUser.getPassword();
        final UserRole userRole = authenticatedUser.getUserRole();
        final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());

        return new User(authenticatedUsername, authenticatedPassword, Collections.singletonList(grantedAuthority));
    }
}