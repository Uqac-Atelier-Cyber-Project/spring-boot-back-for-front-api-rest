package com.uqac.back_for_front.controllers;


import com.uqac.back_for_front.dto.*;
import com.uqac.back_for_front.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * creer un nouvel utilisateur
     * @param request RegisterRequest
     * @return ResponseEntity<String>
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }


    /**
     * connecter un utilisateur
     * @param request LoginRequest
     * @return ResponseEntity<UserResponse>
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/loginHistory")
    public ResponseEntity<LoginHistoryResponse> loginHistory(@RequestBody LoginHistoryRequest request){
        return ResponseEntity.ok(userService.loginHistory(request));
    }

    /**
     * get user data
     * @param request
     * @return
     */
    @PostMapping("/userData")
    public ResponseEntity<UserResponse> userData(@RequestBody UserRequest request){
        return ResponseEntity.ok(userService.userData(request));
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<UserResponse> updateProfile(@RequestBody UpdateProfileRequest request){
        return ResponseEntity.ok(userService.updateProfile(request));
    }
}
