package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.*;
import com.uqac.back_for_front.entity.LoginHistory;
import com.uqac.back_for_front.entity.User;
import com.uqac.back_for_front.repositories.LoginHistoryRepository;
import com.uqac.back_for_front.repositories.UserRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder; // Utilisé pour encoder les mots de passe

    /**
     * Enregistre un nouvel utilisateur
     * @param request Requête d'enregistrement
     * @return Message de succès
     */
    public UserResponse register(RegisterRequest request) {
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

        // enregistrer la connexion
        // TODO : s'assurer que la requette est bien rempli par le front
        LoginHistory loginHistory = LoginHistory.builder()
                .user(user.getUserId())
                .location(request.getLocation())
                .platform(request.getPlatform())
                .loginTime(java.time.Instant.now())
                .build();
        loginHistoryRepository.save(loginHistory);
        return new UserResponse(user.getUserId(), user.getEmail());
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

        // enregistrer la connexion
        // TODO : s'assurer que la requette est bien rempli par le front
        LoginHistory loginHistory = LoginHistory.builder()
            .user(user.getUserId())
            .location(request.getLocation())
            .platform(request.getPlatform())
            .loginTime(java.time.Instant.now())
            .build();
        loginHistoryRepository.save(loginHistory);


        return new UserResponse(user.getUserId(), user.getEmail());
    }

    /**
     * recuperer les informations de l'utilisateur
     * @param request Requête contenant l'id user
     * @return Les informations de l'utilisateur
     */
    public UserResponse userData(UserRequest request) {
        // Rechercher l'utilisateur par ID
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Retourne la réponse
        return new UserResponse(user.getUserId(), user.getEmail());
    }


    /**
     * Met à jour les informations de l'utilisateur
     * @param request Requête contenant les nouvelles informations
     * @return Les informations mises à jour de l'utilisateur
     */
    public UserResponse updateProfile(UpdateProfileRequest request) {
        // Rechercher l'utilisateur par ID
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Mettre à jour les champs fournis
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword())); // Hash du mot de passe
        }

        // Sauvegarde en base de données
        userRepository.save(user);

        // Retourne la réponse
        return new UserResponse(user.getUserId(), user.getEmail());
    }

    /**
     * Récupérer l'historique de connexion de l'utilisateur
     * @param request Requête contenant l'id de l'utilisateur
     * @return L'historique de connexion de l'utilisateur
     */
    public LoginHistoryResponse loginHistory(LoginHistoryRequest request) {
        //verifier si l'utilisateur existe
        if (request.getUser() != null) {
            List<LoginHistory> loginHistoryList = loginHistoryRepository.findByUser(request.getUser());

            return new LoginHistoryResponse(loginHistoryList);
        }
        return new LoginHistoryResponse(null);
    }
}