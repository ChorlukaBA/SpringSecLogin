package com.lm.SpringSecLogin.security.services;

import org.springframework.beans.factory.annotation.Autowired;                      // Per iniezione di dipendenza
import org.springframework.security.core.userdetails.UserDetails;                   // Interfaccia che rappresenta un utente autenticato
import org.springframework.security.core.userdetails.UserDetailsService;            // Interfaccia che carica i dettagli dell'utente (Ha un solo metodo loadUserByUsername, di cui facciamo override)
import org.springframework.security.core.userdetails.UsernameNotFoundException;     // Eccezione che viene lanciata quando non viene trovato l'utente
import org.springframework.stereotype.Service;                                      // Per indicare che si tratta di un bean di servizio
import org.springframework.transaction.annotation.Transactional;                    // Per supportare la transazionalità (gestione delle transazioni, propagazione, timeout, sola lettura, rollbackFor, noRollbackFor)

import com.lm.SpringSecLogin.models.User;
import com.lm.SpringSecLogin.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    UserRepository userRepository;                                                  // Iniettiamo il repository per poter interagire con il database


    // Dato un username, carica i dettagli dell'utente da UserRepository
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));         // Se l'utente non è presente nel database, viene lanciata un'eccezione

        return UserDetailsImpl.build(user);             // Se l'utente è presente, lo crea (UserDetails) con i dettagli trovati e lo restituisce
    }

}