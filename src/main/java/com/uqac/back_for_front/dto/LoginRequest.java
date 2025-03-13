package com.uqac.back_for_front.dto;

import lombok.*;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
