package com.lm.SpringSecLogin.security.jwt;

import com.auth0.jwt.JWT;                               // Import of JWT class from auth0 library, used to create a JWT (JSON Web Token)
import com.auth0.jwt.algorithms.Algorithm;              // Algorithm is needed to sign the JWT token with the secret key, HMAC256 algorithm is used
import com.auth0.jwt.JWTVerifier;                       // JWTVerifier is used to verify the JWT token
import com.auth0.jwt.interfaces.DecodedJWT;             // DecodedJWT is used to decode the JWT token, to get the subject, issuer, expiration date, etc.
import com.lm.SpringSecLogin.model.UserRole;            // UserRole is an enum class that contains the roles of the user
import com.lm.SpringSecLogin.model.User;                // User class is used to get the username and userRole
import lombok.RequiredArgsConstructor;                  // Lombok annotation to create a constructor with all required arguments
import org.springframework.stereotype.Component;        // Spring annotation to define a component class

import java.util.Date;                                  // Date class is used to get the current date and time

/**
 * JwtTokenManager class is used to generate a JWT token, extract the username from the token, validate the token, and check if the token is expired
 */

@Component
@RequiredArgsConstructor
public class JwtTokenManager
{
    private final JwtProperties jwtProperties;

    public String generateToken(User user)
    {
        final String username = user.getUsername();
        final UserRole userRole = user.getUserRole();

        return JWT.create()
                .withSubject(username)
                .withIssuer(jwtProperties.getIssuer())
                .withClaim("role", userRole.name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMinute() * 60 * 1000))      // Expiration time is set in minutes
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));                                         // JWT token is signed with the secret key using HMAC256 algorithm
    }

    // Extract the username from the token
    public String getUsernameFromToken(String token)
    {
        final DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getSubject();
    }

    // Validate the token by checking if the username from the token is equal to the authenticated username and if the token is expired or not
    public boolean validateToken(String token, String authenticatedUsername)
    {
        final String UsernameFromToken = getUsernameFromToken(token);

        final boolean equalsUsername = UsernameFromToken.equals(authenticatedUsername);
        final boolean tokenExpired = isTokenExpired(token);

        return equalsUsername && !tokenExpired;
    }

    // Check if the token is expired by comparing the expiration date from the token with the current date
    private boolean isTokenExpired(String token)
    {
        final Date expirationDateFromToken = getExpirationDateFromToken(token);
        return expirationDateFromToken.before(new Date());
    }

    // Get the expiration date from the token
    private Date getExpirationDateFromToken(String token)
    {
        final DecodedJWT decodedJWT = getDecodedJWT(token);
        return decodedJWT.getExpiresAt();
    }

    // Decode the JWT token, using the secret key to verify the token and return the DecodedJWT object
    private DecodedJWT getDecodedJWT(String token)
    {
        final JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes())).build();

        return jwtVerifier.verify(token);
    }
}