package com.uqac.back_for_front.dto;

import com.uqac.back_for_front.dto.analyseObject.Host;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnalysisCVERequest {
    private String reportId;
    private List<Host> cvehosts;
}
