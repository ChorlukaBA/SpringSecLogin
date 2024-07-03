package com.lm.SpringSecLogin.controller;

import org.springframework.web.bind.annotation.GetMapping;          // Importing GetMapping. Needed to handle GET requests
import org.springframework.web.bind.annotation.RestController;      // Importing RestController. Needed for our Swagger documentation
import org.springframework.http.ResponseEntity;                     // Importing ResponseEntity. Needed to return a response to the client

/*
    This class is a simple controller class that is used to check if the application is running fine.
    It has a single GET endpoint that returns a simple message: "Working fine!".
 */

@RestController
public class HealthController
{
    @GetMapping("/health")
    public ResponseEntity<String> sayHello()
    {
        return ResponseEntity.ok("Working fine!");
    }
}