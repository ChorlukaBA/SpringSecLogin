package com.lm.SpringSecLogin.security.service;

import com.lm.SpringSecLogin.service.UserValidationService;                         // Here we import the UserValidationService, a service class that provides the logic to validate the user
import com.lm.SpringSecLogin.model.User;                                            // Here we import the User, our model class for the user
import com.lm.SpringSecLogin.model.UserRole;                                        // Here we import the UserRole, an enum for the user roles
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;                     // Here we import the AuthenticatedUserDto, our DTO class for the authenticated user
import com.lm.SpringSecLogin.security.dto.RegistrationRequest;                      // Here we import the RegistrationRequest, our DTO class for the registration request
import com.lm.SpringSecLogin.security.dto.RegistrationResponse;                     // Here we import the RegistrationResponse, our DTO class for the registration response
import com.lm.SpringSecLogin.security.mapper.UserMapper;                            // Here we import the UserMapper, a mapper class for the user
import com.lm.SpringSecLogin.repository.UserRepository;                             // Here we import the UserRepository, an interface that extends the JpaRepository interface
import lombok.RequiredArgsConstructor;                                              // Lombok annotation that creates a constructor with all the required arguments
import lombok.extern.slf4j.Slf4j;                                                   // Lombok annotation that creates a logger
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;            // Import for BCryptPasswordEncoder, a password encoder that uses the BCrypt strong hashing function
import org.springframework.stereotype.Service;                                      // Import for Service, an annotation to indicate that the class is a service

/*
    * UserServiceImpl is a service class that provides the implementation for the methods to find the user by username, register a new user, and find the authenticated user by username.
    * It uses the UserRepository to save the user to the database and find the user by username.
    * It uses the BCryptPasswordEncoder to encode the user password.
    * It uses the UserValidationService to validate the user.
    * It uses the UserMapper to convert the RegistrationRequest to a User and the User to an AuthenticatedUserDto.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserValidationService userValidationService;

    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest)
    {
        userValidationService.validateUser(registrationRequest);

        final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER);

        userRepository.save(user);

        final String username = registrationRequest.getUsername();
        final String registrationSuccessMessage = "Registration successful";
        log.info("{} registered successfully!", username);

        return new RegistrationResponse(registrationSuccessMessage);
    }

    @Override
    public AuthenticatedUserDto findAuthenticatedUserByUsername(String username)
    {
        final User user = findByUsername(username);
        return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);
    }
}