package com.uqac.back_for_front.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @Column(name = "report_id", nullable = false)
    private Long reportId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(name = "step1", nullable = false, length = 255)
    private String step1;

    @Column(name = "step2", nullable = false, length = 255)
    private String step2;

    @Column(name = "step3", nullable = false, length = 255)
    private String step3;

    @Column(name = "step4", nullable = false, length = 255)
    private String step4;
}
