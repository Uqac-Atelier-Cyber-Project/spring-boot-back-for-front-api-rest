package com.uqac.back_for_front.dto;

import lombok.*;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String location;
    private String platform;
}