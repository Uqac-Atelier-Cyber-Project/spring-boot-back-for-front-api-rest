package com.uqac.back_for_front.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uqac.back_for_front.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
        /* TODO : remplir methodes */ 

    // Optional<Notification> findByEmail(String email);
}
