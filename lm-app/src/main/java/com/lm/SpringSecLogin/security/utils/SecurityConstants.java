package com.lm.SpringSecLogin.security.utils;

import org.springframework.security.core.Authentication;                // Importo Authentication de Spring Security per gestire l'oggetto di autenticazione
import org.springframework.security.core.context.SecurityContextHolder; // Importo SecurityContextHolder per ottenere l'oggetto di autenticazione
import org.springframework.security.core.userdetails.UserDetails;       // Importo UserDetails per ottenere i dettagli dell'utente autenticato


// Definiamo qui le costanti utilizzate per la sicurezza dell'applicazione

public class SecurityConstants
{
    public static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 ore

    public static final String SECRET_KEY = "mySecretKey"; // Chiave segreta per la crittografia del token

    public static final String ISSUER = "lm"; // Azienda che rilascia il token

    public static final String TOKEN_PREFIX = "Bearer "; // Prefisso del token, utile a distinguere il token dal resto dell'header

    public static final String HEADER_STRING = "Authorization"; // Stringa dell'header

    // Esempio in HttpServletRequest: Authorization: Bearer YWxhZGRpbjpvcGVuc2VzYW1l

    public static final String LOGIN_REQUEST_URI = "/login"; // URI per la richiesta di login

    public static final String REGISTRATION_REQUEST_URI = "/registration"; // URI per la richiesta di registrazione

    private SecurityConstants()
    {
        throw new UnsupportedOperationException("Cannot instantiate this class");
    }

    // Metodo per ottenere l'username autenticato dal SecurityContext
    public static String getAuthenticatedUsername()
    {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Ottengo l'oggetto di autenticazione dal SecurityContext
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Ottengo i dettagli dell'utente autenticato
        return userDetails.getUsername(); // Restituisco il nome utente
    }
}