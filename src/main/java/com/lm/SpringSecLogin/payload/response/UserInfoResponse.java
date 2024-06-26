package com.lm.SpringSecLogin.payload.response;

import java.util.List;                                  // List ci viene utile per gestire i ruoli dell'utente

/*
    Questa classe è un semplice POJO che contiene le informazioni dell'utente che vogliamo restituire come risposta
    al client dopo che l'utente si è autenticato con successo.
    Questo POJO contiene le seguenti informazioni:
    - id: l'ID dell'utente
    - username: il nome utente dell'utente
    - email: l'email dell'utente
    - roles: la lista dei ruoli dell'utente
 */

public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String username, String email, List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }
}