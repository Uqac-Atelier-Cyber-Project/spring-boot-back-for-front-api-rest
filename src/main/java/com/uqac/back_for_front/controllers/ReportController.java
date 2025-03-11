package com.uqac.back_for_front.controllers;


import com.uqac.back_for_front.dto.ReportRequest;
import com.uqac.back_for_front.dto.ReportsRequest;
import com.uqac.back_for_front.dto.ReportsResponse;
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
     * get all reports for a given user
     * @param request UserReportsRequest
     * @return ResponseEntity<reportsResponse>
     */
    @PostMapping("/UserReports")
    public ResponseEntity<ReportsResponse> userReports(@RequestBody ReportsRequest request) {
        return ResponseEntity.ok(reportService.userReports(request));
    }

    /**
     * change isread aribute from a given report
     * @param request ReportRequest
     * @return 200 ok
     */
    @PostMapping("/report-read")
    public ResponseEntity<String> reportRead(@RequestBody ReportRequest request) {
        return ResponseEntity.ok(reportService.reportRead(request));
    }
}
