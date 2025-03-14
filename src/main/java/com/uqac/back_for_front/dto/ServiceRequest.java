package com.uqac.back_for_front.dto;

import com.uqac.back_for_front.entity.SubmitOption;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ServiceRequest {
    private Long reportId;
    private SubmitOption options;
}
