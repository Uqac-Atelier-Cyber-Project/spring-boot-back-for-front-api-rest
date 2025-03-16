package com.uqac.back_for_front.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GenerateReportRequest {
    private Long reportId;
}
