package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDTO {
    private Long reportId;
    private String reportName;
    private String encryptedFile;
    private boolean isRead;

    // Constructeur
    public ReportDTO(Long reportId, String reportName, String encryptedFile, boolean isRead) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.encryptedFile = encryptedFile;
        this.isRead = isRead;
    }
}
