package com.uqac.back_for_front.controllers;


import com.uqac.back_for_front.dto.*;
import com.uqac.back_for_front.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    /**
     * Register a new user
     * @param request RegisterRequest
     * @return ResponseEntity<String>
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("Email: " + request.getEmail());
        return ResponseEntity.ok(userService.register(request));
    }


    /**
     * Login a user
     * @param request LoginRequest
     * @return ResponseEntity<UserResponse>
     */
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
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

    @PostMapping("/connectionHistory")
    public ResponseEntity<LoginHistoryResponse> loginHistory(@RequestBody LoginHistoryRequest request){
        return ResponseEntity.ok(userService.loginHistory(request));
    }
}
