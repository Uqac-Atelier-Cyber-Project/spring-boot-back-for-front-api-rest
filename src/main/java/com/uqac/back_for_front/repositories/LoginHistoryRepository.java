package com.uqac.back_for_front.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uqac.back_for_front.entity.LoginHistory;

import java.util.List;
import java.util.UUID;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {

    List<LoginHistory> findByUser(UUID userId);
}
