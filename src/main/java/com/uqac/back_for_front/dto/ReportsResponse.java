package com.uqac.back_for_front.dto;

import com.uqac.back_for_front.entity.Report;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class ReportsResponse {


    private List<ReportDTO> reports;

    public ReportsResponse(List<ReportDTO> reports) {
        this.reports = reports;
    }

}
