package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.LoginRequest;
import com.uqac.back_for_front.dto.RegisterRequest;
import com.uqac.back_for_front.dto.UserResponse;
import com.uqac.back_for_front.entity.User;
import com.uqac.back_for_front.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Utilisé pour encoder les mots de passe

    /**
     * Enregistre un nouvel utilisateur
     * @param request Requête d'enregistrement
     * @return Message de succès
     */
    public String register(RegisterRequest request) {
        // Vérifie si l'email est déjà utilisé
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email déjà utilisé !");
        }

        // Créer un nouvel utilisateur
        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword())) // Hash du mot de passe
                .build();

        userRepository.save(user);
        return "Utilisateur enregistré avec succès!";
    }

    /**
     * Connecte un utilisateur
     * @param request Requête de connexion
     * @return Réponse de connexion
     */
    public UserResponse login(LoginRequest request) {
        // Vérifier si l'utilisateur existe
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalStateException("Mot de passe incorrect");
        }

        return new UserResponse(user.getUserId(), user.getEmail());
    }
}