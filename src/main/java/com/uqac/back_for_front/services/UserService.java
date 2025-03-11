package com.uqac.back_for_front.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uqac.back_for_front.models.User;
import com.uqac.back_for_front.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        // Logique métier avant l'enregistrement (exemple : validation, hachage du mot de passe, etc.)
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("Email et mot de passe sont requis");
        }
        return userRepository.save(user);  // Enregistrement de l'utilisateur dans la base
    }

    public User getUserByEmail(String email) {
        // Logique métier ou récupération simple
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));  // Recherche dans la base de données
    }

    public String getUserEmail(String email) {
        return userRepository.findByEmail(email)
                .map(User::getEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }
}
