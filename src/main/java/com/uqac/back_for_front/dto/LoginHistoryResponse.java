package com.uqac.back_for_front.dto;

import com.uqac.back_for_front.entity.LoginHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class LoginHistoryResponse {
    private List<LoginHistory> loginHistories;

    public LoginHistoryResponse(List<LoginHistory> loginHistories) {

        this.loginHistories = loginHistories;
    }
}
