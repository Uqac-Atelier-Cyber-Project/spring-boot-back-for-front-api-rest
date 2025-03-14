package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BfsshResponse {
    private String host; // ip address
    private String message;
    private String error;
    private String user; //username
    private String password;

}
