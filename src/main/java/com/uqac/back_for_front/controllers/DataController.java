package com.uqac.back_for_front.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DataController {

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Connexion (à implémenter)");
    }

    @GetMapping("/register")
    public ResponseEntity<String> register() {
        return ResponseEntity.ok("Inscription (à implémenter)");
    }

    @GetMapping("/notifications")
    public ResponseEntity<String> getNotifications() {
        return ResponseEntity.ok("Liste des notifications (à implémenter)");
    }

    @GetMapping("/rapports")
    public ResponseEntity<String> getRapports() {
        return ResponseEntity.ok("Liste des rapports (à implémenter)");
    }

    @GetMapping("/report-read")
    public ResponseEntity<String> reportRead() {
        return ResponseEntity.ok("Marquer un rapport comme lu (à implémenter)");
    }


    @GetMapping("/connection-history")
    public ResponseEntity<String> getConnectionHistory() {
        return ResponseEntity.ok("Historique des connexions (à implémenter)");
    }

    @GetMapping("/user-data")
    public ResponseEntity<String> getUserData() {
        return ResponseEntity.ok("Données de l'utilisateur (à implémenter)");
    }

    @GetMapping("/update-profile")
    public ResponseEntity<String> updateProfile() {
        return ResponseEntity.ok("Mise à jour du profil (à implémenter)");
    }

    @GetMapping("/submit-options")
    public ResponseEntity<String> submitOptions() {
        return ResponseEntity.ok("Soumettre des options (à implémenter)");
    }

}
