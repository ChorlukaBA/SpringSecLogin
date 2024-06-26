package com.lm.SpringSecLogin.security.jwt;

/*
      Definiamo un filtro che esegue una volta per ogni richiesta.
      La classe AuthTokenFilter estende la classe OncePerRequestFilter di Spring Security.
      Qui, facciamo override del metodo doFilterInternal e controlliamo l'header della richiesta per il token JWT.
 */
import java.io.IOException;                                 // Importiamo le classi IOException e ServletException da javax.servlet

import jakarta.servlet.FilterChain;                         // Per gestire le catene di filtri
import jakarta.servlet.ServletException;                    // Per gestire le eccezioni sulle servlet
import jakarta.servlet.http.HttpServletRequest;             // Per gestire le richieste HTTP
import jakarta.servlet.http.HttpServletResponse;            // Per gestire le risposte HTTP

import org.slf4j.Logger;                                                                        // Per il framework di logging
import org.slf4j.LoggerFactory;                                                                 // A differenza Logger, LoggerFactory è un'interfaccia che fornisce metodi per gestire un'istanza di Logger
import org.springframework.beans.factory.annotation.Autowired;                                  // Per l'iniezione delle dipendenze
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;         // AuthManager verifica l'oggetto UsernamePasswordAuthenticationToken e restituisce un oggetto Authentication se ha successo
import org.springframework.security.core.context.SecurityContextHolder;                         // Per ottenere l'autenticazione corrente dell'utente
import org.springframework.security.core.userdetails.UserDetails;                               // Per ottenere i dettagli dell'utente
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;          // Converte un oggetto HttpServletRequest in un oggetto WebAuthenticationDetails, facendo da ponte tra Servlet API e Spring Security
import org.springframework.web.filter.OncePerRequestFilter;                                     // Per creare un filtro che esegue una volta per ogni richiesta

import com.lm.SpringSecLogin.security.services.UserDetailsServiceImpl;                          // Per ottenere i dettagli dell'utente

public class AuthTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private JwtUtils jwtUtils;                          // Iniettiamo l'istanza di JwtUtils

    @Autowired
    private UserDetailsServiceImpl userDetailsService;      // Iniettiamo l'istanza di UserDetailsServiceImpl

    // Logger
    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);                                                                             // Estraiamo il token JWT dai cookies HTTP della richiesta (se presente)
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {                                                        // Se è valido e presente....
                String username = jwtUtils.getUserNameFromJwtToken(jwt);                                                // ...facciamo il parsing dell'username dall'oggetto JWT

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);                              // Tramite l'username possiamo ottenere i dettagli dell'utente

                UsernamePasswordAuthenticationToken authentication =                                                    // Usiamo i dettagli dell'utente per creare un oggetto UsernamePasswordAuthenticationToken
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));                  // Impostiamo i parametri dell'oggetto authentication

                SecurityContextHolder.getContext().setAuthentication(authentication);                                   // Impostiamo il contesto di autenticazione dell'utente attuale
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    // Metodo per estrarre il token JWT dai cookies HTTP della richiesta
    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        return jwt;
    }
}