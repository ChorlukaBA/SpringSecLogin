package com.lm.SpringSecLogin.payload.request;

/*
    POJO per la richiesta di login, contiene tutti i campi necessari per effettuare il login.
 */

import jakarta.validation.constraints.NotBlank;             // Ci consente di validare che un campo non sia nullo o vuoto

public class LoginRequest {
    @NotBlank
    private String username;                                // Nome utente, non può essere nullo o vuoto

    @NotBlank
    private String password;                                // Password, non può essere nullo o vuoto

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}