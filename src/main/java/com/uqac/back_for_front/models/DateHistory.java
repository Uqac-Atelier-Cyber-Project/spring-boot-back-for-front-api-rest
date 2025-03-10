package com.uqac.back_for_front.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DateHistory {
    // Getters and Setters
    private LocalDateTime timestamp;  // ISO 8601 timestamp
    private String device;  // Device information

}