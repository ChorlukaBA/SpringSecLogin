package com.lm.SpringSecLogin.controller;

import com.lm.SpringSecLogin.security.dto.RegistrationRequest;
import com.lm.SpringSecLogin.security.dto.RegistrationResponse;
import com.lm.SpringSecLogin.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController
{
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest)
    {
        final RegistrationResponse registrationResponse = userService.registration(registrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }
}