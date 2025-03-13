//package com.uqac.back_for_front.controllers;
//
//
//import com.uqac.back_for_front.dto.HistoryRequest;
//import com.uqac.back_for_front.dto.HistoryResponse;
//import com.uqac.back_for_front.services.ConHistoryService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/conhistory")
//@RequiredArgsConstructor
//public class ConHistoryController {
//
//    private final ConHistoryService conhistoryService;
//
//    /**
//     * get connexion history for a given user
//     * @param request HistoryRequest
//     * @return ResponseEntity<HistoryResponse>
//     */
//    @PostMapping("/history")
//    public ResponseEntity<HistoryResponse> history(@RequestBody HistoryRequest request) {
//        return ResponseEntity.ok(conhistoryService.history(request));
//    }
//
//}
