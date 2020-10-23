package br.com.tiagoamp.timetracker.controller;

import br.com.tiagoamp.timetracker.dto.*;
import br.com.tiagoamp.timetracker.model.User;
import br.com.tiagoamp.timetracker.security.SecurityConfiguration;
import br.com.tiagoamp.timetracker.security.UserAuth;
import br.com.tiagoamp.timetracker.service.TokenService;
import br.com.tiagoamp.timetracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("timetracker")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;  // Bean created in SecurityConfiguration

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;


    @PostMapping("auth")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody UserAuth userAuth) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(userAuth.getEmail(), userAuth.getPassword());
        Authentication authentication = authManager.authenticate(credentials);
        userAuth = (UserAuth) authentication.getPrincipal();
        String tokenStr = tokenService.generateToken(userAuth.getEmail(), userAuth.getRole());
        return ResponseEntity.ok(new TokenDTO(tokenStr));
    }

}
