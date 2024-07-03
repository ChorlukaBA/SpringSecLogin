package com.lm.SpringSecLogin.security.jwt;

import org.springframework.security.core.AuthenticationException;       // Import the AuthenticationException class, which is thrown when an authentication request is rejected because the credentials are invalid
import org.springframework.security.web.AuthenticationEntryPoint;       // Import the AuthenticationEntryPoint interface, which is used to commence an authentication scheme
import org.springframework.stereotype.Component;                        // Import the Component annotation, which marks a Java class as a Spring component

import javax.servlet.http.HttpServletRequest;                           // Import the HttpServletRequest class, which provides information about the HTTP request
import javax.servlet.http.HttpServletResponse;                          // Import the HttpServletResponse class, which provides HTTP-specific functionality in sending a response
import java.io.IOException;                                             // Import the IOException class, which signals that an I/O exception of some sort has occurred

/**
 * JwtAuthenticationEntryPoint class is used to handle an authentication entry point for JWT in situations where an authentication exception is thrown.
 * It implements the AuthenticationEntryPoint interface.
 * It returns an HTTP status code of 401 (Unauthorized) when an authentication request is rejected because the credentials are invalid.
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException
    {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}