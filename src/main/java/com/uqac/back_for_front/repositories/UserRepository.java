package com.uqac.back_for_front.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uqac.back_for_front.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
