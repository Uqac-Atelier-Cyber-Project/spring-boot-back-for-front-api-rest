package com.uqac.back_for_front.repositories;

import com.uqac.back_for_front.entity.PendingAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Interface PendingAnalysisRepository
 */
public interface PendingAnalysisRepository extends JpaRepository<PendingAnalysis, UUID> {
}