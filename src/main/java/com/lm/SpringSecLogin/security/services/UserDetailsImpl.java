package com.lm.SpringSecLogin.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;                          // Per la gestione dei privilegi dell'utente loggato
import org.springframework.security.core.authority.SimpleGrantedAuthority;          // Consente di avere un wrapper per le stringhe che rappresentano i ruoli
import org.springframework.security.core.userdetails.UserDetails;                   // Se l'autenticazione ha successo, restituisce un oggetto UserDetails

import com.lm.SpringSecLogin.models.User;                                           // Importo la classe User del package models
import com.fasterxml.jackson.annotation.JsonIgnore;                                 // Per evitare la serializzazione di un campo sensibile

public class UserDetailsImpl implements UserDetails
{
    private static final long serialVersionUID = 1L;                                // Per la serializzazione

    private Long id;                                                                // Identificativo dell'utente

    private String username;                                                        // Nome utente

    private String email;                                                           // Email dell'utente

    @JsonIgnore
    private String password;                                                        // Password dell'utente, non deve essere serializzata

    private Collection<? extends GrantedAuthority> authorities;                     // Collection che contiene i ruoli degli utenti

    // Costruttore
    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // Metodo per creare un UserDetailsImpl a partire da un oggetto User (classe del package models), per poi restituirlo
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        // Convertiamo Set<Role> in List<GrantedAuthority>, fondamentale per poter lavorare con Spring Security e l'oggetto Authentication


        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}