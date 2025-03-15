package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.*;
import com.uqac.back_for_front.entity.PendingAnalysis;
import com.uqac.back_for_front.entity.Report;
import com.uqac.back_for_front.entity.Result;
import com.uqac.back_for_front.repositories.PendingAnalysisRepository;
import com.uqac.back_for_front.repositories.ReportRepository;
import com.uqac.back_for_front.repositories.ResultRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository ReportRepository;
    private final PendingAnalysisRepository pendingAnalysisRepository;
    private final ResultRepository resultRepository;
    private final RestTemplate restTemplate;

    /**
     * recuperer tous les rapports pour un utilisateur donné
     * @param request ReportsRequest
     * @return ReportsResponse
     */
    public ReportsResponse userReports(ReportsRequest request) {
        // Récupération des rapports de l'utilisateur donné
        // TODO : verifier les attributs necessaires
        List<Report> reports = ReportRepository.findByUserId(request.getUserId());

        // Création de la réponse
        return new ReportsResponse(reports);
    }

    /**
     * mettre à jour le statut d'un rapport en tant que lu
     * @param request ReportRequest
     * @return String
     */
    public String reportRead(ReportRequest request) {
        Long reportId = request.getReport_id(); // Récupération de l'ID depuis l'objet request

        Optional<Report> optionalReport = ReportRepository.findByReportId(reportId);

        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setIsRead(true); // Marquer comme lu
            ReportRepository.save(report); // Sauvegarder la mise à jour
            return "Le rapport a été marqué comme lu.";
        } else {
            return "Rapport non trouvé.";
        }
    }

    /**
     * soumettre des options et démarrer une analyse
     * @param request SubmitRequest
     * @return SubmitOptionResponse
     */
    public SubmitOptionResponse submitOptions(SubmitRequest request) {

        // Creation du rapport set a null
        Report report = Report.builder()
                .userId(request.getUserId())
                .reportName("Nom_du_rapport"+ java.time.Instant.now()) //set the document name with timestamp
                .isRead(false)
                .triggerDate(java.time.Instant.now())
                .build();

        ReportRepository.save(report);


        // creation resultat set a null
        Result result = Result.builder()
                .reportId(report.getReportId())
                .step1(null)
                .step2(null)
                .step3(null)
                .step4(null)
                .build();

        resultRepository.save(result);

        // Création d'un objet PendingAnalysis
        PendingAnalysis pendingAnalysis = PendingAnalysis.builder()
                .reportId(report.getReportId())
                .user(request.getUserId())
                .step1(!request.getOptions().get(0).isValue())
                .step2(!request.getOptions().get(1).isValue())
                .step3(!request.getOptions().get(2).isValue())
                .step4(!request.getOptions().get(3).isValue())
                .pdfPassword(request.getPdfPassword())
                .build();

        pendingAnalysisRepository.save(pendingAnalysis);

        //appels des differents services

        ServiceRequest serviceRequest = new ServiceRequest();

        String[] UrlsServices = {"http://localhost:8082/urlModuleScan", "http://localhost:8083/urlModuleBfssh", "http://localhost:8084/urlModuleBfwifi", "http://localhost:8085/urlModuleCVE"};

        for (int i = 0; i < 4; i++) {
            if (request.getOptions().get(i).isValue()) {
                serviceRequest.setReportId(report.getReportId());
                serviceRequest.setOptions(request.getOptions().get(i));
                restTemplate.postForObject(UrlsServices[i], serviceRequest, Void.class);
            }
        }

        SubmitOptionResponse submitOptionResponse = new SubmitOptionResponse();
        submitOptionResponse.setMessage("Options submitted successfully");
        return submitOptionResponse;
    }

    /**
     * stocker les resultats du module scan de ports
     * @param request ScanPortsRequest
     * @return ScanPortsResponse
     */
    public ScanPortsResponse scanPorts(ScanPortsRequest request) {
        //stockage des resultats du module scan de ports
        Result result = (Result) resultRepository.findByReportId(request.getReportId());
        result.setStep1("le resultat du module scan de ports");
        resultRepository.save(result);
        return null;
    }

    /**
     * stocker les resultats du module bruteforcessh
     * @param request BfsshRequest
     * @return BfsshResponse
     */
    public BfsshResponse bfssh(BfsshRequest request){
        // stokage des resultats du module brutefrcessh
        Result result = (Result) resultRepository.findByReportId(request.getReportId());
        result.setStep2("le resultat du module bruteforcessh");
        resultRepository.save(result);
        return null;
    }

    /**
     * stocker les resultats du module bruteforceWifi
     * @param request BffWifiRequest
     * @return BffWifiResponse
     */
    public BffWifiResponse bffwifi(BffWifiRequest request) {
        // stokage des resultats du module brutefrcessh
        Result result = (Result) resultRepository.findByReportId(request.getReportId());
        result.setStep3("le resultat du module bruteforceWifi");
        resultRepository.save(result);
        return null;
    }

    /**
     * stocker les resultats du module analyse cve
     * @param request AnalysisCVERequest
     * @return AnalysisCVEResponse
     */
    public AnalysisCVEResponse analysisCVE(AnalysisCVERequest request) {
        // stokage des resultats du module analyse cve
        Result result = (Result) resultRepository.findByReportId(request.getReportId());
        result.setStep4("le resultat du module analyseCVE");
        resultRepository.save(result);
        return null;
    }
}
