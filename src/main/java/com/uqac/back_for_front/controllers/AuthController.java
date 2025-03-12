package com.uqac.back_for_front.controllers;


import com.uqac.back_for_front.dto.LoginRequest;
import com.uqac.back_for_front.dto.RegisterRequest;
import com.uqac.back_for_front.dto.UserResponse;
import com.uqac.back_for_front.dto.UserDataRequest;
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
     * Register a new user
     * @param request RegisterRequest
     * @return ResponseEntity<String>
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
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
    public ResponseEntity<UserResponse> userData(@RequestBody UserDataRequest request){
        return ResponseEntity.ok(userService.userData(request));
    }
}
