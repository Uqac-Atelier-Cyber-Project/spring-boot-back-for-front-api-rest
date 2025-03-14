package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BfsshRequest {
    //private String host; // ip address destination

    private Long reportId;
    private String host; // ip address
    private String message;
    private String error;
    private String user; //username
    private String password;
}
