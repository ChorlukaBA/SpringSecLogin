package com.lm.SpringSecLogin.payload.request;

/*
    La classe SignupRequest è un modello che rappresenta i dati inviati dal client per la registrazione di un nuovo utente.
    La classe contiene i seguenti campi:
    - username: nome utente dell'utente
    - email: email dell'utente
    - role: ruoli dell'utente
    - password: password dell'utente
 */

import java.util.Set;                                  // Necessaria per Set<String> role, che è un insieme di ruoli

import jakarta.validation.constraints.*;               // Necessaria per utilizzare le annotazioni di validazione di Spring

public class SignupRequest
{
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;                            // Nome utente, deve essere non vuoto, lungo tra 3 e 20 caratteri

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;                               // Email, deve essere non vuota, lunga al massimo 50 caratteri e deve essere un'email

    private Set<String> role;                           // Ruoli dell'utente

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;                            // Password, deve essere non vuota, lunga tra 6 e 40 caratteri

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}