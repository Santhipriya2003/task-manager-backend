package com.example.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.dto.LoginRequest;
import com.example.taskmanager.entity.User;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
   private PasswordEncoder encoder;
     // ================= REGISTER =================

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        try {

            // CHECK IF EMAIL ALREADY EXISTS
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body("Email already exists");
            }

            // ENCODE PASSWORD
            user.setPassword(
                    encoder.encode(user.getPassword())
            );

            // DEFAULT ROLE
            if (user.getRole() == null ||
                user.getRole().isEmpty()) {

                user.setRole("ADMIN");
            }

            // SAVE USER
            userRepository.save(user);

            return ResponseEntity.ok(
                    "User registered successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Registration failed: " + e.getMessage());
        }
    }

    // ================= LOGIN =================

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request
    ) {

        try {

            User user = userRepository
                    .findByEmail(request.getEmail())
                    .orElse(null);

            if (user == null) {
                return ResponseEntity
                        .status(401)
                        .body("User not found");
            }

            // CHECK PASSWORD
            if (!encoder.matches(
                    request.getPassword(),
                    user.getPassword()
            )) {

                return ResponseEntity
                        .status(401)
                        .body("Invalid password");
            }

            // GENERATE TOKEN
            String token = jwtUtil.generateToken(
                    user.getEmail()
            );

            // RETURN TOKEN + ROLE
            return ResponseEntity.ok(
                    new AuthResponse(
                            token,
                            user.getRole()
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .internalServerError()
                    .body("Login failed: " + e.getMessage());
        }
    }
}