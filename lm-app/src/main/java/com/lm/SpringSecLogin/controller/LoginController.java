package com.lm.SpringSecLogin.controller;

import com.lm.SpringSecLogin.security.dto.LoginRequest;
import com.lm.SpringSecLogin.security.dto.LoginResponse;
import com.lm.SpringSecLogin.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController
{
    private final JwtTokenService jwtTokenService;

    @PostMapping
    public ResponseEntity<LoginResponse> loginRequest(@Valid @RequestBody LoginRequest loginRequest)
    {
        final LoginResponse loginResponse = jwtTokenService.getLoginResponse(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}