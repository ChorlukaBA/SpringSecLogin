package com.lm.SpringSecLogin.security.service;

import com.lm.SpringSecLogin.model.User;                            // Here we import the User, our model class for the user
import com.lm.SpringSecLogin.security.dto.RegistrationRequest;      // Here we import the RegistrationRequest, our DTO class for the registration request
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;     // Here we import the AuthenticatedUserDto, our DTO class for the authenticated user
import com.lm.SpringSecLogin.security.dto.RegistrationResponse;     // Here we import the RegistrationResponse, our DTO class for the registration response

/*
    * UserService is an interface that provides the methods to find the user by username, register a new user, and find the authenticated user by username.
    * It is implemented by the UserServiceImpl class.
 */

public interface UserService
{
    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);
}