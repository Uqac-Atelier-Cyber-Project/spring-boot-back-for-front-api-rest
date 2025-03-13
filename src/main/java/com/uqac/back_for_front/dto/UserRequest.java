package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRequest {
    //@NotNull
    private UUID userId;
}
