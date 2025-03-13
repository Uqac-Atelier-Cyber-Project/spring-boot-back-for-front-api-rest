package com.uqac.back_for_front.dto;

import com.uqac.back_for_front.entity.LoginHistory;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class HistoryResponse {
    private List<LoginHistory> connections;

    public HistoryResponse(List<LoginHistory> connections) {
        this.connections = connections;
    }
}
