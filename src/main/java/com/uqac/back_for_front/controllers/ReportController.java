package com.uqac.back_for_front.controllers;


import com.uqac.back_for_front.dto.*;
import com.uqac.back_for_front.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * soumettre les options choisies par l'utilisateur
     * @param request SubmitRequest
     * @return SubmitOptionResponse
     */
    @PostMapping("/submitOptions")
    public ResponseEntity<SubmitOptionResponse> submitOptions(@RequestBody SubmitRequest request) {
        return ResponseEntity.ok(reportService.submitOptions(request));
    }

    /**
     * reponse du module scanPorts
     * @param request ScanPortsRequest
     * @return ScanPortsResponse
     */
    @PostMapping("/scanPorts")
    public ResponseEntity<ScanPortsResponse> scanPorts(@RequestBody ScanPortsRequest request){
        return ResponseEntity.ok(reportService.scanPorts(request));
    }

    /**
     * reponse du module bruteforceSSH
     * @param request BruteforceSSHRequest
     * @return BruteforceSSHResponse
     */
    @PostMapping("/bfssh")
    public ResponseEntity<BfsshResponse> bfssh(@RequestBody BfsshRequest request){
        return ResponseEntity.ok(reportService.bfssh(request));
    }

    /**
     * reponse du module bruteforceWifi
     * @param request BruteforceWifiRequest
     * @return BruteforceWifiResponse
     */
    @PostMapping("/bffwifi")
    public ResponseEntity<BffWifiResponse> bffwifi(@RequestBody BffWifiRequest request){
        return ResponseEntity.ok(reportService.bffwifi(request));
    }

    /**
     * reponse du module analyseCVE
     * @param request AnalysisCVERequest
     * @return AnalysisCVEResponse
     */
    @PostMapping("/analysisCVE")
    public ResponseEntity<AnalysisCVEResponse> analysisCVE(@RequestBody AnalysisCVERequest request){
        return ResponseEntity.ok(reportService.analysisCVE(request));
    }

    /**
     * recuperer les rapports d'un utilisateur
     * @param request UserReportsRequest
     * @return ResponseEntity<reportsResponse>
     */
    @PostMapping("/UserReports")
    public ResponseEntity<ReportsResponse> userReports(@RequestBody ReportsRequest request) {
        return ResponseEntity.ok(reportService.userReports(request));
    }

    /**
     * mise à jour de l'état d'un rapport (mis a lu)
     * @param request ReportRequest
     * @return 200 ok
     */
    @PostMapping("/report-read")
    public ResponseEntity<String> reportRead(@RequestBody ReportRequest request) {
        return ResponseEntity.ok(reportService.reportRead(request));
    }
}
