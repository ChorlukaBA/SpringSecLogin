package com.lm.SpringSecLogin.security.jwt;

import lombok.Getter;                                                           // Lombok annotation to create all getters
import lombok.Setter;                                                           // Lombok annotation to create all setters
import org.springframework.boot.context.properties.ConfigurationProperties;     // We get our properties from application.yml file
import org.springframework.context.annotation.Configuration;                    // Spring annotation to define a configuration class

/**
 * JwtProperties class is used to store the properties of JWT token (issuer, secretKey, expirationMinute)
 * We get our properties from application.yml file where the prefix is jwt
 */

@Configuration
@ConfigurationProperties(prefix = "jwt")                          // We get our properties from application.yml file where the prefix is jwt
@Getter
@Setter
public class JwtProperties
{
    private String issuer;
    private String secretKey;
    private long expirationMinute;
}