package com.uqac.back_for_front.services;

import com.uqac.back_for_front.dto.HistoryRequest;
import com.uqac.back_for_front.dto.HistoryResponse;
import com.uqac.back_for_front.entity.LoginHistory;
import com.uqac.back_for_front.repositories.ConHistoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ConHistoryService {

    private final ConHistoryRepository conhistoryRepository;

    public HistoryResponse history(HistoryRequest request) {
        UUID userId = request.getUserId(); // Extraction de l'ID utilisateur depuis la requête

        List<LoginHistory> connections = conhistoryRepository.findByUserId(userId); // recuperation de toutes les connexion d'un user

        return new HistoryResponse(connections);
    }

}
