package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.ReportsRequest;
import com.uqac.back_for_front.entity.Report;
import com.uqac.back_for_front.repositories.ReportRepository;
import com.uqac.back_for_front.dto.ReportResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository ReportRepository;

    public ReportResponse userReports(ReportsRequest request) {
        // Récupération des rapports de l'utilisateur donné
        List<Report> reports = ReportRepository.findByUserId(request.getUserId());

        // Création de la réponse
        return new ReportResponse(reports);
    }
}
