package com.lm.SpringSecLogin.controllers;

import org.springframework.security.access.prepost.PreAuthorize;                // Libreria necessaria a @PreAuthorize, per definire i ruoli necessari per accedere alle API
import org.springframework.web.bind.annotation.CrossOrigin;                     // Libreria per indicare le origini consentite per le richieste HTTP
import org.springframework.web.bind.annotation.GetMapping;                      // Libreria per la gestione delle richieste GET
import org.springframework.web.bind.annotation.RequestMapping;                  // Libreria per la gestione delle richieste HTTP
import org.springframework.web.bind.annotation.RestController;                  // Libreria per la gestione dei controller REST

/*
    Ci sono qui 4 API:
        - /api/test/all per tutti gli utenti (pubblico)
        - /api/test/user per gli utenti registrati (USER, MODERATOR, ADMIN)
        - /api/test/mod per i moderatori (MODERATOR)
        - /api/test/admin per gli amministratori (ADMIN)

     PreAuthorize ci consente di definire i ruoli necessari per accedere alle API (USER, MODERATOR, ADMIN).
     Questo grazie al fatto che abbiamo WebSecurityConfig che definisce i ruoli e le autorizzazioni, tramite @EnableMethodSecurity(prePostEnabled = true).
 */

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)                                      // Per consentire richieste da qualsiasi origine, con maxAge di 3600 secondi per la memorizzazione nella cache
@RestController                                                                 // Indica che è un controller REST
@RequestMapping("/api/test")                                                    // Mappa le richieste HTTP all'URL /api/test
public class TestController
{
    @GetMapping("/all")
    public String allAccess()
    {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess()
    {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess()
    {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess()
    {
        return "Admin Board.";
    }
}