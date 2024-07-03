package com.lm.SpringSecLogin.security.jwt;

import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;                             // Here we import the AuthenticatedUserDto, our DTO class for the authenticated user
import com.lm.SpringSecLogin.security.dto.LoginRequest;                                     // Here we import the LoginRequest, our DTO class for the login request
import com.lm.SpringSecLogin.security.dto.LoginResponse;                                    // Here we import the LoginResponse, our DTO class for the login response
import com.lm.SpringSecLogin.security.mapper.UserMapper;                                    // Here we import the UserMapper, our mapper class for the User
import com.lm.SpringSecLogin.security.service.UserService;                                  // Here we import the UserService, our service class for the User
import com.lm.SpringSecLogin.model.User;                                                    // Here we import the User, our model class for the User
import lombok.RequiredArgsConstructor;                                                      // Here we import the RequiredArgsConstructor, a Lombok annotation to create a constructor with all the required arguments
import lombok.extern.slf4j.Slf4j;                                                           // Here we import the log4j, a Lombok annotation to create a logger
import org.springframework.security.authentication.AuthenticationManager;                   // Here we import the AuthenticationManager, an interface for authentication manager objects
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;     // Here we import the UsernamePasswordAuthenticationToken, a class for username and password authentication token
import org.springframework.stereotype.Service;                                              // Here we import the Service, an annotation to indicate that the class is a service

/**
 * JwtTokenService is a service class that provides the logic to generate a JWT token for the user when they log in.
 * It uses the AuthenticationManager to authenticate the user and the JwtTokenManager to generate the token.
 * It also uses the UserService to find the authenticated user by username and the UserMapper to convert the AuthenticatedUserDto to User.
 * It returns a LoginResponse with the generated token.
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenService
{
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;

    public LoginResponse getLoginResponse(LoginRequest loginRequest)
    {
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();

        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

        final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);
        final String token = jwtTokenManager.generateToken(user);

        log.info("{} has successfully logged in!", user.getUsername());

        return new LoginResponse(token);
    }
}