package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import com.uqac.back_for_front.entity.SubmitOption;


@Getter
@Setter
public class SubmitRequest {
    private UUID userId;
    private List<SubmitOption> options;
}
