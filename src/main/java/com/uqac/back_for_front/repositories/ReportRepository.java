package com.uqac.back_for_front.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uqac.back_for_front.models.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
        /* TODO : remplir methodes */ 

    // Optional<Report> findByEmail(String email);
}
