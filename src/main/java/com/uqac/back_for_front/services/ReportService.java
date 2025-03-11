package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.ReportsRequest;
import com.uqac.back_for_front.dto.ReportRequest;
import com.uqac.back_for_front.entity.Report;
import com.uqac.back_for_front.repositories.ReportRepository;
import com.uqac.back_for_front.dto.ReportsResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository ReportRepository;

    public ReportsResponse userReports(ReportsRequest request) {
        // Récupération des rapports de l'utilisateur donné
        // TODO : verifier les attributs necessaires
        List<Report> reports = ReportRepository.findByUserId(request.getUserId());

        // Création de la réponse
        return new ReportsResponse(reports);
    }

    public String reportRead(ReportRequest request) {
        UUID reportId = request.getReport_id(); // Récupération de l'ID depuis l'objet request

        Optional<Report> optionalReport = ReportRepository.findById(reportId);

        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setIsRead(true); // Marquer comme lu
            ReportRepository.save(report); // Sauvegarder la mise à jour
            return "Le rapport a été marqué comme lu.";
        } else {
            return "Rapport non trouvé.";
        }
    }



}
