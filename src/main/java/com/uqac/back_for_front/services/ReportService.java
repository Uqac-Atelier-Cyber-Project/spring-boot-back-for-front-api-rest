package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.*;
import com.uqac.back_for_front.dto.analyseObject.Host;
import com.uqac.back_for_front.entity.PendingAnalysis;
import com.uqac.back_for_front.entity.Report;
import com.uqac.back_for_front.entity.Result;
import com.uqac.back_for_front.repositories.PendingAnalysisRepository;
import com.uqac.back_for_front.repositories.ReportRepository;
import com.uqac.back_for_front.repositories.ResultRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReportService {

    @Autowired
    private ApiProperties apiProperties;

    private final ReportRepository reportRepository;
    private final PendingAnalysisRepository pendingAnalysisRepository;
    private final ResultRepository resultRepository;
    private final RestTemplate restTemplate;
    private static final Logger logger = Logger.getLogger(ReportService.class.getName());

    /**
     * recuperer tous les rapports pour un utilisateur donné
     *
     * @param request ReportsRequest
     * @return ReportsResponse
     */
    public ReportsResponse userReports(ReportsRequest request) {
        // verification
        if (!reportRepository.existsByUserId(request.getUserId())) {
            return new ReportsResponse(new ArrayList<>()); // si l'utilisateur n'as pas de rapport, retourner une liste vide
        }
        // Récupération des rapports de l'utilisateur donné
        List<Report> reports = reportRepository.findByUserIdAndEncryptedFileIsNotNull(request.getUserId());

        // Création de la réponse avec transformation en DTO
        List<ReportDTO> reportDTOs = reports.stream()
                .map(report -> new ReportDTO(
                        report.getReportId(),
                        report.getReportName(),
                        report.getEncryptedFile(),
                        report.getIsRead()
                ))
                .collect(Collectors.toList());

        return new ReportsResponse(reportDTOs);
    }


    /**
     * soumettre des options et démarrer une analyse
     *
     * @param request SubmitRequest
     * @return SubmitOptionResponse
     */
    public SubmitOptionResponse submitOptions(SubmitRequest request) {

        // Creation du rapport set a null
        Report report = Report.builder()
                .userId(request.getUserId())
                //fait  en sorte que le nom soit reportName + random int
                .reportName("Nom_du_rapport" + new Random().nextInt()) //set the document name with timestamp
                .isRead(false)
                .triggerDate(java.time.Instant.now())
                .build();

        reportRepository.save(report);


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
                .pdfPassword(request.getPdfPassword())
                .build();

        pendingAnalysisRepository.save(pendingAnalysis);

        //appels des differents services

        ServiceRequest serviceRequest = new ServiceRequest();

        String[] UrlsServices = {apiProperties.getUrl_scan() + "/api/execute-cpp", apiProperties.getUrl_ssh() + "/api/execute-cpp", apiProperties.getUrl_wifi() + "/api/attack", apiProperties.getUrl_cve() + "/scan/target"};

        for (int i = 0; i < 4; i++) {
            if (request.getOptions().get(i).isValue()) {
                serviceRequest.setReportId(report.getReportId());
                serviceRequest.setOption(request.getOptions().get(i).getOption1());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<ServiceRequest> entity = new HttpEntity<>(serviceRequest, headers);

                try {
                    restTemplate.postForObject(UrlsServices[i], entity, String.class);
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
     *
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
     *
     * @param request BfsshRequest
     * @return BfsshResponse
     */
    public BfsshResponse bfssh(BfsshRequest request) {
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
     *
     * @param request BffWifiRequest
     * @return BffWifiResponse
     */
    public BffWifiResponse bffwifi(BffWifiRequest request) {
        logger.info("APPEL BFFWIFI");

        // Retrieve the list of results for the given report ID
        List<Result> results = resultRepository.findByReportId(request.getReportId());

        if (results.isEmpty()) {
            // Handle the case where no results are found
            return BffWifiResponse.builder().message("No results found for the given report ID").build();
        }

        // Assuming you want to update the first result in the list
        Result result = results.getFirst();

        String resultWifi = request.toString();

        logger.info("Result Wifi: " + resultWifi);

        result.setStep3(resultWifi);

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
     * mettre à jour le statut d'un rapport en tant que lu
     *
     * @param request ReportRequest
     * @return String
     */
    public String reportRead(ReportReadRequest request) {
        Long reportId = request.getReportId();
        UUID userId = request.getUserId();

        Optional<Report> optionalReport = reportRepository.findByReportIdAndUserId(reportId, userId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setIsRead(true);
            reportRepository.save(report);
        }
        else {
            return "Rapport non trouvé.";
        }

//        Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(reportId);
//
//        if (optionalPendingAnalysis.isPresent()) {
//            PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
//            pendingAnalysis.setStep1(true);
//            pendingAnalysis.setStep2(true);
//            pendingAnalysis.setStep3(true);
//            pendingAnalysis.setStep4(true);
//            pendingAnalysisRepository.save(pendingAnalysis);
//        } else {
//            return "PendingAnalysis non trouvé.";
//        }

        return "Rapport marqué comme lu.";
    }


    /**
     * stocker les resultats du module analyse cve
     *
     * @param request AnalysisCVERequest
     * @return AnalysisCVEResponse
     */
    public AnalysisCVEResponse analysisCVE(AnalysisCVERequest request) {

        // Retrieve the list of results for the given report ID
        List<Result> results = resultRepository.findByReportId(Long.valueOf(request.getReportId()));

        if (results.isEmpty()) {
            // Handle the case where no results are found
            return AnalysisCVEResponse.builder().message("No results found for the given report ID").build();
        }

        // Assuming you want to update the first result in the list
        Result result = results.getFirst();
        List<Host> cveList = request.getCvehosts();

        result.setStep4(cveList.toString());

        // Update the result in the repository
        resultRepository.save(result);

        // Update the PendingAnalysis step3 to true
        try {
            Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(Long.valueOf(request.getReportId()));

            if (optionalPendingAnalysis.isPresent()) {
                PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
                pendingAnalysis.setStep4(true);
                pendingAnalysisRepository.save(pendingAnalysis);
            } else {
                return AnalysisCVEResponse.builder().message("PendingAnalysis not found").build();
            }
        } catch (javax.persistence.NonUniqueResultException e) {
            // Si plusieurs résultats sont renvoyés pour une seule ID
            return AnalysisCVEResponse.builder().message("Multiple PendingAnalysis found for this report ID").build();
        } catch (Exception e) {
            // Gérer d'autres exceptions
            return AnalysisCVEResponse.builder().message("An error occurred: " + e.getMessage()).build();
        }
        return AnalysisCVEResponse.builder().message("CVE analysis request submitted successfully").build();
    }

    /**
     * retourner les rapports disponibles pour un utilisateur
     *
     * @param request ReportRequest
     * @return String
     */
    public List<String> reportAvailable(ReportRequest request) {

        // Find all reports wich file-encrypted is null for a specific userid

        List<Report> reports = reportRepository.findByUserId(request.getUserId());
        List<Report> reportsPending = reports.stream().filter(report -> report.getEncryptedFile() == null).toList();

        List<String> pendingReports = new ArrayList<>();
        // communication avec le service de generation de rapport pour générer le rapport
        String urlService = apiProperties.getUrl_report() + "/reportGenerate/generate";
        // If there is a report available
        // check pendingAnalysis for each reportid
        for (Report report : reportsPending) {
            Optional<PendingAnalysis> optionalPendingAnalysis = pendingAnalysisRepository.findByReportId(report.getReportId());
            if (optionalPendingAnalysis.isPresent()) {
                PendingAnalysis pendingAnalysis = optionalPendingAnalysis.get();
                if (!(pendingAnalysis.getStep1() && pendingAnalysis.getStep2() && pendingAnalysis.getStep3() && pendingAnalysis.getStep4())) {
                    pendingReports.add(report.getReportName() + ": PENDING");
                } else {
                    try {
                        GenerateReportRequest generateReportRequest = GenerateReportRequest.builder().reportId(report.getReportId()).build();

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        HttpEntity<GenerateReportRequest> entity = new HttpEntity<>(generateReportRequest, headers);

                        restTemplate.postForObject(urlService, entity, String.class);
                    } catch (RestClientException e) {
                        // Log the error and the response body
                        System.err.println("Error calling service: " + urlService);
                        System.err.println("Response body: " + e.getMessage());
                        throw e; // Re-throw the exception after logging
                    }
                }
            }
        }


        // recupérer la liste pour un USER dans la table report where encryptedFile is not null and isRead is false
        List<Report> reportsAvailable = reports.stream().filter(report -> report.getEncryptedFile() != null && !report.getIsRead()).toList();
        // on rajoute AVAILABLE pour chaque report
        for (Report report : reportsAvailable) {
            pendingReports.add(report.getReportName() + ": AVAILABLE");
        }

        // return les deux listes

        return pendingReports;
    }
}
