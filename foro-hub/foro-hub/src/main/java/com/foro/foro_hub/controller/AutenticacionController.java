package com.foro.foro_hub.controller;

import com.foro.foro_hub.dto.LoginDTO;
import com.foro.foro_hub.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());

        Authentication authentication = authenticationManager.authenticate(authToken);

        String token = tokenService.generarToken(authentication.getName());

        return ResponseEntity.ok(new TokenResponse(token));
    }

    public record TokenResponse(String token) {}
}