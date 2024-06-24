package com.lm.SpringSecLogin.security;

import org.springframework.beans.factory.annotation.Autowired;                          // Iniezione delle dipendenze
import org.springframework.context.annotation.Bean;                                     // Bean sono oggetti che vengono gestiti dal container Spring
import org.springframework.context.annotation.Configuration;                            // Utilizzato per definire la classe di configurazione

import org.springframework.security.authentication.AuthenticationManager;                                           // Gestione dell'autenticazione
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;                                   // Implementazione di AuthenticationProvider che utilizza un UserDetailsService
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;     // Configurazione dell'autenticazione
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;                    // Abilita la sicurezza basata su metodi (AOP). Sfrutta @PreAuthorize, @PostAuthorize, @Secured
import org.springframework.security.config.annotation.web.builders.HttpSecurity;                                    // Configurazione della sicurezza HTTP
import org.springframework.security.config.http.SessionCreationPolicy;                                              // Configurazione della sessione
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;                                            // Implementazione di PasswordEncoder che utilizza l'algoritmo di hashing BCrypt
import org.springframework.security.crypto.password.PasswordEncoder;                                                // Interfaccia per la codifica delle password
import org.springframework.security.web.SecurityFilterChain;                                                        // Gestione della catena di filtri di sicurezza per vulnerabilità, su protocollo HTTP
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;                        // Filtro di autenticazione che autentica gli utenti con i loro nomi utente e password

import com.lm.SpringSecLogin.security.jwt.AuthEntryPointJwt;
import com.lm.SpringSecLogin.security.jwt.AuthTokenFilter;
import com.lm.SpringSecLogin.security.services.UserDetailsServiceImpl;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity                           // Abilita la sicurezza basata su metodi (AOP). Sfrutta @PreAuthorize, @PostAuthorize, @Secured
//(securedEnabled = true,
//jsr250Enabled = true,
//prePostEnabled = true) // by default
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;                  // Implementazione di UserDetailsService che carica gli utenti da un'origine dati

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;              // Per gestire i tentativi di accesso non autorizzati

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter()
    {
        return new AuthTokenFilter();
    }   // Filtro per l'autenticazione JWT

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();           // Istanziamo l'oggetto DAO che servirà per gestire l'autenticazione

        authProvider.setUserDetailsService(userDetailsService);                             // Settiamo l'oggetto UserDetailsService, che servirà per recuperare gli utenti
        authProvider.setPasswordEncoder(passwordEncoder());                                 // Settiamo l'oggetto PasswordEncoder, che servirà per codificare le password

        return authProvider;                                                                // Restituiamo l'oggetto DAO
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception
    {
        return authConfig.getAuthenticationManager();                                       // Restituiamo l'oggetto AuthenticationManager (per gestire l'autenticazione), relativo all'authconfig attuale
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }       // Indichiamo qui il tipo di codifica delle password


    // Configurazione della sicurezza HTTP, con l'uso di un filtro di autenticazione JWT
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/test/**").permitAll()
                                .anyRequest().authenticated()
                );

        // Diciamo a SpringSecurity come configurare CORS e CSRF, quando richiedere l'autenticazione e come gestire le richieste non autorizzate
        // Utilizziamo AuthTokenFilter per gestire le richieste JWT e richiediamo di filtrare le richieste prima di UsernamePasswordAuthenticationFilter
        // Usiamo poi qui AuthEntryPointJwt per gestire le eccezioni di accesso non autorizzato

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}