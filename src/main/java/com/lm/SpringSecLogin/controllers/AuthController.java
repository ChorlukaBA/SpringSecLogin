package com.lm.SpringSecLogin.controllers;

// Librerie necessarie alla gestione delle strutture dati
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;                                                // Libreria per la validazione dei dati in input

import org.springframework.beans.factory.annotation.Autowired;                  // Libreria per l'iniezione delle dipendenze
import org.springframework.http.HttpHeaders;                                    // Libreria per la gestione degli header HTTP
import org.springframework.http.ResponseCookie;                                 // Libreria per la gestione dei cookie
import org.springframework.http.ResponseEntity;                                 // Libreria per la gestione delle risposte HTTP
import org.springframework.security.authentication.AuthenticationManager;                   // Libreria per la gestione dell'autenticazione
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;     // Libreria per i token di autenticazione username/password
import org.springframework.security.core.Authentication;                                    // Token di autenticazione, rappresenta l'identità dell'utente autenticato
import org.springframework.security.core.context.SecurityContextHolder;                     // Necessaria per gestire il contesto di sicurezza dell'applicazione in un dato momento
import org.springframework.security.crypto.password.PasswordEncoder;                        // Libreria per la codifica delle password
import org.springframework.web.bind.annotation.CrossOrigin;                                 // Libreria per indicare le origini consentite per le richieste HTTP
import org.springframework.web.bind.annotation.PostMapping;                                 // Libreria per la gestione delle richieste POST
import org.springframework.web.bind.annotation.RequestBody;                                 // Libreria per la gestione dei dati in input da una richiesta HTTP
import org.springframework.web.bind.annotation.RequestMapping;                              // Libreria per la gestione delle richieste HTTP
import org.springframework.web.bind.annotation.RestController;                              // Libreria per la gestione dei controller REST

import com.lm.SpringSecLogin.models.ERole;
import com.lm.SpringSecLogin.models.Role;
import com.lm.SpringSecLogin.models.User;
import com.lm.SpringSecLogin.payload.request.LoginRequest;
import com.lm.SpringSecLogin.payload.request.SignupRequest;
import com.lm.SpringSecLogin.payload.response.UserInfoResponse;
import com.lm.SpringSecLogin.payload.response.MessageResponse;
import com.lm.SpringSecLogin.repository.RoleRepository;
import com.lm.SpringSecLogin.repository.UserRepository;
import com.lm.SpringSecLogin.security.jwt.JwtUtils;
import com.lm.SpringSecLogin.security.services.UserDetailsImpl;

/*
    Questo controller fornisce le API per la registrazione, il login e il logout degli utenti.
    Il controller è annotato con @CrossOrigin per consentire le richieste da qualsiasi origine, che sia localhost:8081 o altri, abbia maxAge di 3600 secondi.
    Il controller è annotato con @RestController per indicare che è un controller REST.
    Il controller è annotato con @RequestMapping("/api/auth") per mappare le richieste HTTP all'URL /api/auth.
    Il controller ha tre metodi:
       - authenticateUser() per autenticare un utente e generare un token JWT.
       - registerUser() per registrare un nuovo utente.
       - logoutUser() per eliminare il token JWT dal cookie.
 */

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")               // Per consentire richieste solo da localhost:8081
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));         // Autenticazione dell'utente tramite UsernamePasswordAuthenticationToken

        SecurityContextHolder.getContext().setAuthentication(authentication);                                                           // Aggiorniamo il SecurityContext con l'oggetto Authentication

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();                                                  // Estraiamo l'oggetto UserDetailsImpl dall'oggetto Authentication

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);                                                             // Generiamo un cookie JWT

        List<String> roles = userDetails.getAuthorities().stream()                                                                      // Estraiamo i ruoli dell'utente dal UserDetailsImpl
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())                                                 // Restituiamo una risposta HTTP con il cookie JWT e i dettagli dell'utente
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
    {
        // Controlliamo se l'username o l'email sono già presenti nel database, in caso affermativo restituiamo un messaggio di errore
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account, con ruolo di default ROLE_USER se non specificato diversamente
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));                                   // Codifichiamo la password dell'utente

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);                                                                      // Salviamo l'utente nel database tramite il repository

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser()
    {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();                                           // Eliminiamo il cookie JWT
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));                                 // Restituiamo un messaggio di logout, con il cookie JWT eliminato
    }
}