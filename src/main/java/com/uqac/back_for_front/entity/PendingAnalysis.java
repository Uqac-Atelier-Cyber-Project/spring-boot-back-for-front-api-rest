package com.uqac.back_for_front.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "pending_analyses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PendingAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_id", nullable = false)
    private Long reportId;

    /*@OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;*/

    @JoinColumn(name = "user_id", nullable = false)
    private UUID user;

    @Column(name = "step1", nullable = false)
    private Boolean step1;

    @Column(name = "step2", nullable = false)
    private Boolean step2;

    @Column(name = "step3", nullable = false)
    private Boolean step3;

    @Column(name = "step4", nullable = false)
    private Boolean step4;

    @Column(name = "pdfPassword", nullable = false)
    private String pdfPassword;
}
