package br.com.tiagoamp.timetracker.service;

import br.com.tiagoamp.timetracker.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(String email, Role role) {
        LocalDateTime expLDT = LocalDateTime.now().plusSeconds(Long.valueOf(expiration));
        Date expiration = Date.from(expLDT.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setIssuer("TimeTracker API")
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .setAudience(role.getAuthority())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValid(String jwt) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Claims extractClaims(String jwt) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(jwt).getBody();
    }

}
