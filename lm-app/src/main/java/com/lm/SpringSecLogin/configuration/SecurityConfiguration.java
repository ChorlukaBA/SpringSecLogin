package com.lm.SpringSecLogin.configuration;

import com.lm.SpringSecLogin.security.jwt.JwtAuthenticationFilter;                          // Import of JwtAuthenticationFilter class. We use JwtAuthenticationFilter class to authenticate a user using JWT.
import com.lm.SpringSecLogin.security.jwt.JwtAuthenticationEntryPoint;                      // Import of JwtAuthenticationEntryPoint class. We use JwtAuthenticationEntryPoint class to handle unauthorized requests.
import lombok.RequiredArgsConstructor;                                                      // Import of RequiredArgsConstructor annotation. We use Lombok's RequiredArgsConstructor annotation to automatically generate a constructor with required arguments.
import org.springframework.context.annotation.Bean;                                         // Import of Bean class. We use Bean class to define a method that returns an object that should be registered as a bean in the Spring application context.
import org.springframework.context.annotation.Configuration;                                // Import of Configuration class. We use Configuration class to define a class as a configuration class in Spring application context.
import org.springframework.security.authentication.AuthenticationManager;                   // Import of AuthenticationManager class. We use AuthenticationManager to handle authentication requests.
import org.springframework.security.config.annotation.web.builders.HttpSecurity;            // Import of HttpSecurity class. We use HttpSecurity class to configure web-based security at a resource level.
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;                  // Import of EnableGlobalMethodSecurity annotation. Needed to secure methods using annotations.
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;         // Import of AuthenticationConfiguration class. We use AuthenticationConfiguration class to configure authentication.
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;                              // Import of EnableWebSecurity annotation. Needed to enable web security in a Spring application.
import org.springframework.security.config.http.SessionCreationPolicy;                                                  // We manage user sessions in a stateless manner.
import org.springframework.security.web.SecurityFilterChain;                                                            // Import of SecurityFilterChain class. Needed to filter incoming requests.
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;                            // Used to authenticate a user using a username and password.

/*
 * SecurityConfiguration class allows us to configure security settings for our application.
 * That means we can configure which endpoints are secured and which are not.
 */


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration
{
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // We configure the security filter chain to handle incoming requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/register", "/health", "/login", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/actuator/**")     // We permit all requests to these endpoints.
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }
}