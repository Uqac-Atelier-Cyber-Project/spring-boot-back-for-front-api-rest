package com.uqac.back_for_front.repositories;


import com.uqac.back_for_front.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * interface ConHistoryRepository
 */
public interface ConHistoryRepository extends JpaRepository<LoginHistory, UUID> {
    // Trouver toutes les connexions d'un utilisateur donné par son ID
    List<LoginHistory> findByUserId(UUID userId);
}
