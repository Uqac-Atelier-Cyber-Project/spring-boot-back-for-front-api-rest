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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository ReportRepository;
    private final PendingAnalysisRepository pendingAnalysisRepository;
    private final ResultRepository resultRepository;
    private final RestTemplate restTemplate;
    private static final Logger logger = Logger.getLogger(ReportService.class.getName());
    private static final int MAX_STEP3_LENGTH = 4000; // Define the maximum length for step3
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
        Long reportId = request.getReport_id();

        Optional<Report> optionalReport = ReportRepository.findByReportId(reportId);

        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setIsRead(true);
            ReportRepository.save(report);
        } else {
            return "Rapport non trouvé.";
        }

        Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(reportId);

        if (optionalPendingAnalysis.isPresent()) {
            PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
            pendingAnalysis.setStep1(true);
            pendingAnalysis.setStep2(true);
            pendingAnalysis.setStep3(true);
            pendingAnalysis.setStep4(true);
            pendingAnalysisRepository.save(pendingAnalysis);
        } else {
            return "PendingAnalysis non trouvé.";
        }

        return "Rapport marqué comme lu.";
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
                .step1(!request.getOptions().get(0).isValue())
                .step2(!request.getOptions().get(1).isValue())
                .step3(!request.getOptions().get(2).isValue())
                .step4(!request.getOptions().get(3).isValue())
                .build();

        pendingAnalysisRepository.save(pendingAnalysis);

        //appels des differents services

        ServiceRequest serviceRequest = new ServiceRequest();

        String[] UrlsServices = {"http://localhost:8082/api/execute-cpp", "http://localhost:8083/api/execute-cpp", "http://localhost:8084/api/attack", "http://localhost:8085/urlModuleCVE"};

        for (int i = 0; i < 4; i++) {
            if (request.getOptions().get(i).isValue()) {
                serviceRequest.setReportId(report.getReportId());
                serviceRequest.setOption(request.getOptions().get(i).getOption1());
                try {
                    restTemplate.postForObject(UrlsServices[i], serviceRequest, String.class);
                } catch (RestClientException e) {
                    // Log the error and the response body
                    System.err.println("Error calling service: " + UrlsServices[i]);
                    System.err.println("Response body: " + e.getMessage());
                    throw e; // Re-throw the exception after logging
                }
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
    public ScanPortsResponse scanPorts(PortScanResultDTO request) {

        logger.info(request.toString());

        // Retrieve the list of results for the given report ID
        List<Result> results = resultRepository.findByReportId(request.getReportId());

        if (results.isEmpty()) {
            // Handle the case where no results are found
            return ScanPortsResponse.builder().message("No results found for the given report ID").build();
        }

        // Assuming you want to update the first result in the list
        Result result = results.getFirst();
        result.setStep1(request.toJSon());

        // Update the result in the repository
        resultRepository.save(result);


        // Update the PendingAnalysis step2 to true
        Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(request.getReportId());

        if (optionalPendingAnalysis.isPresent()) {
            PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
            pendingAnalysis.setStep1(true);
            pendingAnalysisRepository.save(pendingAnalysis);
        } else {
            return ScanPortsResponse.builder().message("PendingAnalysis not found").build();
        }

        return ScanPortsResponse.builder().message("Bruteforce SSH request submitted successfully").build();
    }

    /**
     * stocker les resultats du module bruteforcessh
     * @param request BfsshRequest
     * @return BfsshResponse
     */
    public BfsshResponse bfssh(BfsshRequest request){
        // Retrieve the list of results for the given report ID
        List<Result> results = resultRepository.findByReportId(request.getReportId());

        if (results.isEmpty()) {
            // Handle the case where no results are found
            return BfsshResponse.builder().message("No results found for the given report ID").build();
        }

        // Assuming you want to update the first result in the list
        Result result = results.getFirst();
        result.setStep2(request.resultJson());

        // Update the result in the repository
        resultRepository.save(result);

        // Update the PendingAnalysis step2 to true
        Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(request.getReportId());

        if (optionalPendingAnalysis.isPresent()) {
            PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
            pendingAnalysis.setStep2(true);
            pendingAnalysisRepository.save(pendingAnalysis);
        } else {
            return BfsshResponse.builder().message("PendingAnalysis not found").build();
        }

        return BfsshResponse.builder().message("Bruteforce SSH request submitted successfully").build();
    }

    /**
     * stocker les resultats du module bruteforceWifi
     * @param request BffWifiRequest
     * @return BffWifiResponse
     */
    public BffWifiResponse bffwifi(BffWifiRequest request) {

        // Retrieve the list of results for the given report ID
        List<Result> results = resultRepository.findByReportId(request.getReportId());

        if (results.isEmpty()) {
            // Handle the case where no results are found
            return BffWifiResponse.builder().message("No results found for the given report ID").build();
        }

        // Assuming you want to update the first result in the list
        Result result = results.getFirst();
        String logContent = request.getLogContent();

        // Truncate the log content if it exceeds the maximum length
        if (logContent.length() > MAX_STEP3_LENGTH) {
            logContent = logContent.substring(logContent.length() - MAX_STEP3_LENGTH);
        }

        result.setStep3(logContent);

        // Update the result in the repository
        resultRepository.save(result);

        // Update the PendingAnalysis step3 to true
        Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(request.getReportId());

        if (optionalPendingAnalysis.isPresent()) {
            PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
            pendingAnalysis.setStep3(true);
            pendingAnalysisRepository.save(pendingAnalysis);
        } else {
            return BffWifiResponse.builder().message("PendingAnalysis not found").build();
        }

        return BffWifiResponse.builder().message("Bruteforce SSH request submitted successfully").build();
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
