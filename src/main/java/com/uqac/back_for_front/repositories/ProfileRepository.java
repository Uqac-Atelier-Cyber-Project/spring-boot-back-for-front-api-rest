package com.uqac.back_for_front.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uqac.back_for_front.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
        /* TODO : remplir methodes */ 

    // Optional<Profile> findByEmail(String email);
}
