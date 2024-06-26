package com.lm.SpringSecLogin.security.jwt;

/*
    Classe che implementa l'interfaccia AuthenticationEntryPoint di Spring Security.
    Facciamo Override del metodo commence per gestire le eccezioni di autenticazione.
    Verrà chiamato ogni volta che un utente non autenticato fa una richiesta HTTP per una risorsa protetta.
    In tal caso, lancia una eccezione AuthenticationException.
 */

import java.io.IOException;                                                         // Per la gestione delle eccezioni di I/O
import java.util.HashMap;                                                           // Per la gestione delle hashmap
import java.util.Map;

import jakarta.servlet.ServletException;                                            // Per la gestione delle eccezioni sulle servlet
import jakarta.servlet.http.HttpServletRequest;                                     // Per la gestione delle richieste HTTP
import jakarta.servlet.http.HttpServletResponse;                                    // Per la gestione delle risposte HTTP

import org.slf4j.Logger;                                                            // Per il framework di logging
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;                                          // Per la gestione dei tipi MIME
import org.springframework.security.core.AuthenticationException;                   // Per la gestione delle eccezioni sollevate da autenticazione invalida
import org.springframework.security.web.AuthenticationEntryPoint;                   // Definisce l'entrypoint dove poi gestire le eccezioni di autenticazione
import org.springframework.stereotype.Component;                                    // Per definire la classe come componente

import com.fasterxml.jackson.databind.ObjectMapper;                                 // Per la gestione dei dati JSON, in particolare per la serializzazione e deserializzazione

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint
{

    // Creiamo un logger per la classe
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException
    {
        logger.error("Unauthorized error: {}", authException.getMessage());             // Loggiamo l'errore di autenticazione

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);                      // Impostiamo il tipo MIME della risposta a application/json
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);                        // Impostiamo lo status della risposta a 401 Unauthorized, per indicare che l'utente non è autorizzato

        // Per fornire dettagli sulla risposta, creiamo una mappa con i dettagli dell'errore
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        // Usiamo l'oggetto ObjectMapper per scrivere la mappa come JSON nella risposta
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}