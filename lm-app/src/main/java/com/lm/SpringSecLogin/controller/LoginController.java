package com.lm.SpringSecLogin.controller;

import com.lm.SpringSecLogin.security.dto.LoginRequest;             // Import of LoginRequest, so that we can handle its objects
import com.lm.SpringSecLogin.security.dto.LoginResponse;            // Import of LoginResponse, so that we can handle its objects
import com.lm.SpringSecLogin.security.jwt.JwtTokenService;          // Given that we base our login on JWT, we need to import JwtTokenService
import lombok.RequiredArgsConstructor;                              // Lombok annotation to generate a constructor with required arguments
import org.springframework.http.ResponseEntity;                     // Import of ResponseEntity, so that we can return a response to the client
import org.springframework.web.bind.annotation.*;                   // Import of RestController, RequestMapping, PostMapping, RequestBody, CrossOrigin

import javax.validation.Valid;                          // Import of Valid, so that we can validate the input of the user

/*
    The LoginController class is responsible for handling the login requests of the user.
    It is annotated with @RestController, @RequestMapping, @PostMapping, @CrossOrigin and @RequiredArgsConstructor.
    The @RestController annotation is used to define the class as a controller.
    The @RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods.
    The @PostMapping annotation is used to handle POST requests.
    The @CrossOrigin annotation is used to handle Cross-Origin Resource Sharing (CORS).
    The @RequiredArgsConstructor annotation is used to generate a constructor with required arguments.
    The class has a single method loginRequest, which takes a LoginRequest object as input and returns a LoginResponse object.
    The method is annotated with @PostMapping, which means that it handles POST requests.
    The method calls the getLoginResponse method of the jwtTokenService object, which returns a LoginResponse object.
    The method returns a ResponseEntity object with the LoginResponse object and an OK status.
 */

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