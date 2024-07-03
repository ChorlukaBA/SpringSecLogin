package com.lm.SpringSecLogin.controller;

import com.lm.SpringSecLogin.security.dto.RegistrationRequest;      // Importing RegistrationRequest class from security/dto package. We use this class to get the request from the user.
import com.lm.SpringSecLogin.security.dto.RegistrationResponse;     // Importing RegistrationResponse class from security/dto package. We use this class to send the response to the user.
import com.lm.SpringSecLogin.security.service.UserService;          // Importing UserService class from security/service package. We use this class to register the user.
import lombok.RequiredArgsConstructor;                              // Importing RequiredArgsConstructor annotation from lombok package. We use this annotation to generate a constructor with required arguments.
import org.springframework.http.ResponseEntity;                     // Importing ResponseEntity class from org.springframework.http package. We use this class to return the response to the user.
import org.springframework.web.bind.annotation.*;                   // Importing RestController, RequestMapping, PostMapping, RequestBody, CrossOrigin annotations from org.springframework.web.bind.annotation package. We use these annotations to create RESTful web services.
import org.springframework.http.HttpStatus;                         // Importing HttpStatus class. We use this class to return the HTTP status code to the user.

import javax.validation.Valid;                                      // We use this annotation to validate the request body.

/*
    * This class is a RestController class that handles the registration request from the user.
    * It has a single method registrationRequest() that takes RegistrationRequest object as a parameter and returns RegistrationResponse object.
 */

@CrossOrigin
@RestController
@RequestMapping("/register")                             // This annotation is used to map the request URL.
@RequiredArgsConstructor
public class RegistrationController
{
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> registrationRequest(@Valid @RequestBody RegistrationRequest registrationRequest)
    {
        final RegistrationResponse registrationResponse = userService.registration(registrationRequest);     // Calling registration() method of UserService class and passing registrationRequest object as a parameter.
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);                        // Returning the response to the user.
    }
}