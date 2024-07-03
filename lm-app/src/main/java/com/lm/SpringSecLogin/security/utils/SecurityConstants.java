package com.lm.SpringSecLogin.security.utils;

import org.springframework.security.core.Authentication;                    // Import for Authentication, an interface for authentication objects
import org.springframework.security.core.context.SecurityContextHolder;     // Import for SecurityContextHolder, a strategy for storing security context information
import org.springframework.security.core.userdetails.UserDetails;           // Import for UserDetails, a core interface which loads user-specific data

/*
    * SecurityConstants is a utility class that provides the constants for our security configuration.
    * It provides the expiration time for the token, the secret key, the issuer, the token prefix, the header string, and the login and registration request URIs.
    * It also provides a method to get the authenticated username.
 */

public class SecurityConstants
{
    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hours

    public static final String SECRET_KEY = "mySecretKey";

    public static final String ISSUER = "lm";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    // i.e. in HttpServletRequest: Authorization: Bearer YWxhZGRpbjpvcGVuc2VzYW1l

    public static final String LOGIN_REQUEST_URI = "/login";

    public static final String REGISTRATION_REQUEST_URI = "/registration";

    private SecurityConstants()
    {
        throw new UnsupportedOperationException("Cannot instantiate this class");
    }

    public static String getAuthenticatedUsername()
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}