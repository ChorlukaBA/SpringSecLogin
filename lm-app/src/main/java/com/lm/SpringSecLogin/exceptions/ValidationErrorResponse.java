package com.lm.SpringSecLogin.exceptions;

import lombok.AllArgsConstructor;               // lombok annotation to create a constructor with all arguments
import lombok.Getter;                           // lombok annotation to create getters
import lombok.NoArgsConstructor;                // lombok annotation to create a constructor with no arguments
import lombok.Setter;                           // lombok annotation to create setters
import org.springframework.http.HttpStatus;     // Spring annotation for HTTP status codes

import java.time.LocalDateTime;                 // Import for LocalDateTime, to get the current time
import java.util.List;                          // Import for List, to store multiple messages

/**
 * This class is used to create a custom error response for validation errors, which will be returned to the client.
 * The response will contain the HTTP status code, the current time, and a list of messages.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponse
{
    private HttpStatus status;
    private LocalDateTime time;
    private List<String> message;

}