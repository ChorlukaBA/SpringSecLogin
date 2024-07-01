package com.lm.SpringSecLogin.security.mapper;

import com.lm.SpringSecLogin.security.dto.RegistrationRequest;
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;
import com.lm.SpringSecLogin.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper
{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(RegistrationRequest registrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

    User convertToUser(AuthenticatedUserDto authenticatedUserDto);
}