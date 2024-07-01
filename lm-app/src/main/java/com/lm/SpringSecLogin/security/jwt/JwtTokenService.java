package com.lm.SpringSecLogin.security.jwt;

import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;
import com.lm.SpringSecLogin.security.dto.LoginRequest;
import com.lm.SpringSecLogin.security.dto.LoginResponse;
import com.lm.SpringSecLogin.security.mapper.UserMapper;
import com.lm.SpringSecLogin.security.service.UserService;
import com.lm.SpringSecLogin.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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