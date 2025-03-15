package com.uqac.back_for_front.repositories;

import com.uqac.back_for_front.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Interface ResultRepository
 */
public interface ResultRepository extends JpaRepository<Result, UUID> {
    List<Result> findByReportId(Long reportId);

}
