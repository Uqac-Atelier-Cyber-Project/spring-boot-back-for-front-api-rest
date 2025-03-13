package com.uqac.back_for_front.dto;
import com.uqac.back_for_front.entity.Report;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class ReportsResponse {
    private List<Report> reports;

    public ReportsResponse(List<Report> reports) {
        this.reports = reports;
    }

    public List<Report> getReports() {
        return reports;
    }
}
