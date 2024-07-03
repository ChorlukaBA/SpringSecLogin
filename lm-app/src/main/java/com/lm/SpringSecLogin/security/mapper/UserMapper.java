package com.lm.SpringSecLogin.security.mapper;

import com.lm.SpringSecLogin.security.dto.RegistrationRequest;                   // Here we import the RegistrationRequest, our DTO class for the registration request
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;                 // Here we import the AuthenticatedUserDto, our DTO class for the authenticated user
import com.lm.SpringSecLogin.model.User;                                        // Here we import the User, our model class for the User
import org.mapstruct.Mapper;                                                    // Here we import the Mapper, an annotation to indicate that the class is a mapper
import org.mapstruct.ReportingPolicy;                                           // Here we import the ReportingPolicy, an enum to define the reporting policy for unmapped properties
import org.mapstruct.factory.Mappers;                                           // Here we import the Mappers, a factory class to get the mapper instance

/**
 * UserMapper is a mapper class that provides the logic to convert the RegistrationRequest to User and the AuthenticatedUserDto to User.
 * It uses the MapStruct library to generate the mapping code at compile time.
 * It defines the INSTANCE constant to get the mapper instance.
 * It defines the convertToUser method to convert the RegistrationRequest to User, when a new user registers.
 * It defines the convertToAuthenticatedUserDto method to convert the User to AuthenticatedUserDto, and vice versa, when the user logs in.
 */

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)              // Here we define the unmappedTargetPolicy to ignore the unmapped properties
public interface UserMapper
{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(RegistrationRequest registrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

    User convertToUser(AuthenticatedUserDto authenticatedUserDto);
}