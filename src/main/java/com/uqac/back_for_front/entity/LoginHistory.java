package com.uqac.back_for_front.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "login_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_id", nullable = false)
    private Long loginId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "login_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private java.time.Instant loginTime;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "platform", nullable = false, length = 100)
    private String platform;
}
