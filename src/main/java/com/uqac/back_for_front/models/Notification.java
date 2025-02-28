package com.uqac.back_for_front.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification {

    /* TODO : remplir getters + setters */ 

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // private Long id;

    // @Column(unique = true, nullable = false)
    // private String email;

    // @Column(nullable = false)
    // private String hashPasswdB64;
}
