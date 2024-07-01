package com.lm.SpringSecLogin.security.service;

import com.lm.SpringSecLogin.model.UserRole;                                      // Importo la classe UserRole del package models
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;                    // Importo la classe AuthenticatedUserDto del package dto
import lombok.RequiredArgsConstructor;                                               // Per iniettare le dipendenze
import lombok.extern.slf4j.Slf4j;                                                    // Per il logging
import org.springframework.security.core.authority.SimpleGrantedAuthority;          // Per la gestione dei privilegi dell'utente loggato
import org.springframework.security.core.userdetails.User;                          // Classe che rappresenta un utente autenticato
import org.springframework.security.core.userdetails.UserDetails;                   // Interfaccia che rappresenta un utente autenticato
import org.springframework.security.core.userdetails.UserDetailsService;            // Interfaccia che carica i dettagli dell'utente (Ha un solo metodo loadUserByUsername, di cui facciamo override)
import org.springframework.security.core.userdetails.UsernameNotFoundException;     // Eccezione che viene lanciata quando non viene trovato l'utente
import org.springframework.stereotype.Service;                                      // Per indicare che si tratta di un bean di servizio

import java.util.Collections;                                                       // Per la gestione delle collezioni
import java.util.Objects;                                                           // Per la gestione degli oggetti


@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final String USERNAME_OR_PASSWORD_INVALID = "Invalid username or password"; // Messaggio di errore per l'eccezione UsernameNotFoundException

    private final UserService userService;                                           // UserService per la gestione degli utenti

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        final AuthenticatedUserDto authenticatedUser = userService.findAuthenticatedUserByUsername(username); // Cerco l'utente per nome utente

        if (Objects.isNull(authenticatedUser)) // Se l'utente non è presente
        {
            throw new UsernameNotFoundException(USERNAME_OR_PASSWORD_INVALID); // Lancio l'eccezione
        }

        final String authenticatedUsername = authenticatedUser.getUsername(); // Ottengo il nome utente
        final String authenticatedPassword = authenticatedUser.getPassword(); // Ottengo la password
        final UserRole userRole = authenticatedUser.getUserRole(); // Ottengo il ruolo dell'utente
        final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name()); // Creo un'istanza di SimpleGrantedAuthority con il ruolo dell'utente

        return new User(authenticatedUsername, authenticatedPassword, Collections.singletonList(grantedAuthority)); // Restituisco un oggetto User con il nome utente, la password e il ruolo dell'utente
    }
}