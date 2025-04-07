package com.uqac.back_for_front.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "api.externe")
public class ApiProperties {
    private String url_report;
    private String url_scan;
    private String url_ssh;
    private String url_wifi;
    private String url_cve;
}
