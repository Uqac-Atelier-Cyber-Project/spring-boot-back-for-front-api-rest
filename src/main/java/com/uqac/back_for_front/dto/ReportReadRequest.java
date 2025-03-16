package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReportReadRequest {
    private Long reportId;
    private UUID userId;

}
