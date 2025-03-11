package com.uqac.back_for_front.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", nullable = false)
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "report_name", length = 255)
    private String reportName;

    @Column(name = "encrypted_file", length = 255)
    private String encryptedFile;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "trigger_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.time.Instant triggerDate;
}
