package com.example.demo.controller;

import com.example.demo.request.LoginRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.JwtResponse;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.user.UserDetail;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    ));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            UserDetail userDetail =  (UserDetail) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetail.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse("login success", jwtResponse));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse("invalid email or password", null));
        }
    }
}
