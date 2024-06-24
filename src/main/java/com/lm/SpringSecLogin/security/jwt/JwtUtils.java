package com.lm.SpringSecLogin.security.jwt;
/*
    Utilities per la gestione dei token JWT (JSON Web Token).
    La classe ha più metodi:
        - getJwtFromCookies: Estrae il token JWT dai cookie HTTP
        - generateJwtCookie: Genera un cookie JWT a partire dai dettagli dell'utente (Username, Data, Scadenza, Firma)
        - getCleanJwtCookie: Genera un cookie JWT vuoto (usato per il logout)
        - getUserNameFromJwtToken: Restituisce l'username dall'oggetto JWT
        - ValidateJwtToken: Verifica la validità del token JWT, tramite la firma (secret)
 */

import java.security.Key;                                           // Per la gestione delle chiavi
import java.util.Date;                                              // Per la gestione delle date

import jakarta.servlet.http.Cookie;                                 // Per gestire i cookie HTTP
import jakarta.servlet.http.HttpServletRequest;                     // Per gestire le richieste HTTP

import org.slf4j.Logger;                                            // Per il framework di logging
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;          // Per iniettare i valori presenti nel file application.properties
import org.springframework.http.ResponseCookie;                     // Per gestire i cookie HTTP presenti nella risposta
import org.springframework.stereotype.Component;                    // Per definire la classe come componente
import org.springframework.web.util.WebUtils;                       // Utilità per la gestione delle richieste HTTP

import com.lm.SpringSecLogin.security.services.UserDetailsImpl;     // Per ottenere i dettagli dell'utente
import io.jsonwebtoken.*;                                           // Per la gestione dei token JWT
import io.jsonwebtoken.io.Decoders;                                 // Per la decodifica dei token JWT
import io.jsonwebtoken.security.Keys;                               // Per la gestione delle chiavi

@Component
public class JwtUtils
{
    // Creiamo un logger per la classe
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    // Iniettiamo i valori presenti nel file application.properties
    @Value("${lm.SpringSecLogin.jwtSecret}")
    private String jwtSecret;

    @Value("${lm.SpringSecLogin.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${lm.SpringSecLogin.jwtCookieName}")
    private String jwtCookie;

    // Estrae il token JWT dai cookie
    public String getJwtFromCookies(HttpServletRequest request)
    {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);     // Estraiamo il cookie JWT dalla richiesta HTTP
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    // Genera un cookie JWT a partire dai dettagli dell'utente
    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal)
    {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername());
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
        return cookie;
    }

    // Genera un cookie JWT vuoto (usato per il logout)
    public ResponseCookie getCleanJwtCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        return cookie;
    }

    // Restituisce l'username dall'oggetto JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Estrae la chiave segreta dal file application.properties
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Verifica la validità del token JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    // Genera un token JWT a partire dall'username
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }
}