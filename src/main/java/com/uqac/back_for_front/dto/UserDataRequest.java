package com.uqac.back_for_front.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
public class UserDataRequest {
    //@NotNull
    private UUID userId;

    //@Email
    private String email;

    //@Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;
}
