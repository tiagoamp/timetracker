package br.com.tiagoamp.timetracker.security;

import br.com.tiagoamp.timetracker.model.Role;
import br.com.tiagoamp.timetracker.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public TokenAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty() || authHeader.length() <= 7) {
            chain.doFilter(request, response);
            return;
        }
        String jwt = authHeader.substring(7, authHeader.length());
        boolean isValid = tokenService.isTokenValid(jwt);
        if (isValid) {
            Claims claims = tokenService.extractClaims(jwt);
            Role role = Role.valueOf(claims.getAudience());
            UserAuth userAuth = new UserAuth(claims.getSubject(), null, role);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userAuth, null, userAuth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

}
