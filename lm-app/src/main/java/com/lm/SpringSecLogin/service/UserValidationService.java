package com.lm.SpringSecLogin.service;

import com.lm.SpringSecLogin.utils.ExceptionMessageAccessor;
import com.lm.SpringSecLogin.exceptions.RegistrationException;
import com.lm.SpringSecLogin.repository.UserRepository;
import com.lm.SpringSecLogin.security.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserValidationService
{
    private static final String EMAIL_ALREADY_EXISTS = "Email already exists"; // Messaggio di errore per l'email già esistente
    private static final String USERNAME_ALREADY_EXISTS = "Username_already_exists"; // Messaggio di errore per il nome utente già esistente

    private final UserRepository userRepository; // UserRepository per la gestione degli utenti
    private final ExceptionMessageAccessor exceptionMessageAccessor; // ExceptionMessageAccessor per l'accesso ai messaggi di errore

    public void validateUser(RegistrationRequest registrationRequest)
    {
        final String email = registrationRequest.getEmail(); // Ottengo l'email dalla richiesta di registrazione
        final String username = registrationRequest.getUsername(); // Ottengo il nome utente dalla richiesta di registrazione

        checkEmail(email); // Controllo l'email
        checkUsername(username); // Controllo il nome utente
    }

    private void checkUsername(String username)
    {
        final boolean existsByUsername = userRepository.existsByUsername(username); // Verifico se il nome utente esiste già

        if (existsByUsername) // Se il nome utente esiste già
        {
            log.warn("Username already exists: {}", username); // Loggo l'errore
            //final String existsUsername = exceptionMessageAccessor.getMessage(null,USERNAME_ALREADY_EXISTS); // Ottengo il messaggio di errore per il nome utente già esistente
            final String existsUsername = "Username already exists"; // Ottengo il messaggio di errore per il nome utente già esistente
            throw new RegistrationException(existsUsername); // Lancio un'eccezione
        }
    }

    private void checkEmail(String email)
    {
        final boolean existsByEmail = userRepository.existsByEmail(email); // Verifico se l'email esiste già

        if (existsByEmail) // Se l'email esiste già
        {
            log.warn("Email already exists: {}", email); // Loggo l'errore
            // final String existsEmail = exceptionMessageAccessor.getMessage(null,EMAIL_ALREADY_EXISTS); // Ottengo il messaggio di errore per l'email già esistente
            final String existsEmail = "Email already exists"; // Ottengo il messaggio di errore per l'email già esistente
            throw new RegistrationException(existsEmail); // Lancio un'eccezione
        }
    }
}