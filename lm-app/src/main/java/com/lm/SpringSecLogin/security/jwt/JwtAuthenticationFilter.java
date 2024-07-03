package com.lm.SpringSecLogin.security.jwt;

import com.lm.SpringSecLogin.security.service.UserDetailsServiceImpl;                       // Import of UserDetailsServiceImpl, useful for loading user details
import com.lm.SpringSecLogin.security.utils.SecurityConstants;                              // Import of SecurityConstants, that contains the login and registration request URI and the header string
import lombok.RequiredArgsConstructor;                                                      // Import of RequiredArgsConstructor, useful for creating a constructor with all the required fields
import lombok.extern.slf4j.Slf4j;                                                           // Import of slf4j, useful for logging
import org.apache.commons.lang3.StringUtils;                                                // Import of StringUtils, useful for working with Strings
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;     // This allows us to create an authentication token given a username and password
import org.springframework.security.core.context.SecurityContextHolder;                     // Needed in order to get the current security context or set it
import org.springframework.security.core.context.SecurityContext;                           // Needed in order to operate with the security context
import org.springframework.web.filter.OncePerRequestFilter;                                 // We extend this class in order to create a filter that only executes once per request
import org.springframework.security.core.userdetails.UserDetails;                           // Needed in order to operate with the user details
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;      // Needed in order to get the details of the authentication
import org.springframework.stereotype.Service;                                              // Needed in order to mark this class as a service

import javax.servlet.FilterChain;                                                           // We use the filterchain defined in our doFilterInternal method
import javax.servlet.ServletException;                                                      // We use this exception in case of a servlet exception
import javax.servlet.http.HttpServletRequest;                                               // We use this class in order to get the request
import javax.servlet.http.HttpServletResponse;                                              // We use this class in order to get the response
import java.io.IOException;                                                                 // We use this exception in case of an IO exception
import java.util.Objects;                                                                   // We use this class in order to work with objects

/**
 * JwtAuthenticationFilter class is used to filter the incoming requests and check the presence of JWT in the header.
 * It extends the OncePerRequestFilter class.
 * It is used to authenticate the user based on the JWT token.
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException
    {
        final String requestURI = req.getRequestURI();
        if (requestURI.contains(SecurityConstants.LOGIN_REQUEST_URI) || requestURI.contains(SecurityConstants.REGISTRATION_REQUEST_URI))
        {
            chain.doFilter(req, res);
            return;
        }

        final String header = req.getHeader(SecurityConstants.HEADER_STRING);
        String username = null;
        String authToken = null;
        if (Objects.nonNull(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX))
        {
            authToken = header.replace(SecurityConstants.TOKEN_PREFIX, StringUtils.EMPTY);
            try
            {
                username = jwtTokenManager.getUsernameFromToken(authToken);
            }
            catch (Exception e)
            {
                log.error("Authentication Exception: {}", e.getMessage());
            }
        }

        final SecurityContext securityContext = SecurityContextHolder.getContext();

        if (Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication()))
        {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenManager.validateToken(authToken, userDetails.getUsername()))
            {
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("Authentication successful. Logged in username: {}", username);
                securityContext.setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);
    }
}