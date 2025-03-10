package com.uqac.back_for_front.controllers;

import com.uqac.back_for_front.models.DateHistory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String id;
    private String email;
    private String password;
    private List<DateHistory> historyConnection;
}