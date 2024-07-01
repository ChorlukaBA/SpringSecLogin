package com.lm.SpringSecLogin.security.service;

import com.lm.SpringSecLogin.model.User;
import com.lm.SpringSecLogin.security.dto.RegistrationRequest;
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;
import com.lm.SpringSecLogin.security.dto.RegistrationResponse;

public interface UserService
{
    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);
}