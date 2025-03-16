package com.uqac.back_for_front.repositories;


import com.uqac.back_for_front.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

/**
 * interface ReportRepository
 */
public interface ReportRepository extends JpaRepository<Report, UUID> {
    // Trouver tous les rapports d'un utilisateur donné par son ID
    List<Report> findByUserId(UUID userId);

    List<Report> findByUserIdAndEncryptedFileIsNotNull(UUID userId);

    Optional<Report> findByReportIdAndUserId(Long reportId, UUID userId);


    boolean existsByUserId(UUID userId);
}


