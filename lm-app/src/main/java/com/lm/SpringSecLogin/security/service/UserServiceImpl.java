package com.lm.SpringSecLogin.security.service;

import com.lm.SpringSecLogin.service.UserValidationService;                         // Importiamo il servizio UserValidationService
import com.lm.SpringSecLogin.model.User;                                            // Importiamo la classe User
import com.lm.SpringSecLogin.model.UserRole;                                        // Importiamo la classe UserRole
import com.lm.SpringSecLogin.security.dto.AuthenticatedUserDto;                         // Importiamo la classe AuthenticatedUserDto
import com.lm.SpringSecLogin.security.dto.RegistrationRequest;                          // Importiamo la classe RegistrationRequest
import com.lm.SpringSecLogin.security.dto.RegistrationResponse;                         // Importiamo la classe RegistrationResponse
import com.lm.SpringSecLogin.security.mapper.UserMapper;                                // Importiamo la classe UserMapper
import com.lm.SpringSecLogin.repository.UserRepository;                                 // Importiamo la classe UserRepository
import lombok.RequiredArgsConstructor;                                              // Importiamo l'annotazione RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j;                                                    // Importiamo l'annotazione slf4j
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;             // Importiamo la classe BCryptPasswordEncoder (per la crittografia della password)
import org.springframework.stereotype.Service;                                      // Importiamo l'annotazione Service

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService
{
    private static final String REGISTRATION_SUCCESSFUL = "Registration successful"; // Messaggio di successo per la registrazione

    private final UserRepository userRepository;                                     // UserRepository per la gestione degli utenti
    private final BCryptPasswordEncoder bCryptPasswordEncoder;                       // BCryptPasswordEncoder per la crittografia della password
    private final UserValidationService userValidationService;                     // UserValidationService per la validazione dell'utente
    @Override
    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);                             // Restituisco l'utente con il nome utente specificato
    }

    @Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest)
    {
        userValidationService.validateUser(registrationRequest);                   // Validiamo l'utente

        final User user = UserMapper.INSTANCE.convertToUser(registrationRequest); // Converto la richiesta di registrazione in un oggetto User
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));        // Critto la password dell'utente
        user.setUserRole(UserRole.USER);                                           // Imposto il ruolo dell'utente come USER

        userRepository.save(user);                                                  // Salvo l'utente nel database

        final String username = registrationRequest.getUsername();                  // Ottengo il nome utente
        // final String registrationSuccessMessage = generalMessageAccessor.getMessage(null, REGISTRATION_SUCCESSFUL, username); // Ottengo il messaggio di successo per la registrazione
        final String registrationSuccessMessage = "Registration successful"; // Ottengo il messaggio di successo per la registrazione
        log.info("{} registered successfully!", username);                        // Loggo il successo della registrazione

        return new RegistrationResponse(registrationSuccessMessage);               // Restituisco un oggetto RegistrationResponse con il messaggio di successo
    }

    @Override
    public AuthenticatedUserDto findAuthenticatedUserByUsername(String username)
    {
        final User user = findByUsername(username);                                 // Ottengo l'utente con il nome utente specificato
        return UserMapper.INSTANCE.convertToAuthenticatedUserDto(user);            // Converto l'utente in un oggetto AuthenticatedUserDto
    }
}