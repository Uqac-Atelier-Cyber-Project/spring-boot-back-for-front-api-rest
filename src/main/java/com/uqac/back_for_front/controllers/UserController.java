package com.uqac.back_for_front.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uqac.back_for_front.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> payload) {
        boolean success = userService.registerUser(payload.get("email"), payload.get("hash_passwd_b64"));
        return success ? ResponseEntity.ok("Inscription réussie") : ResponseEntity.status(401).body("Email déjà utilisé");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> payload) {
        boolean success = userService.authenticateUser(payload.get("email"), payload.get("hash_passwd_b64"));
        return success ? ResponseEntity.ok("Connexion réussie") : ResponseEntity.status(401).body("Email ou mot de passe incorrect");
    }
}
