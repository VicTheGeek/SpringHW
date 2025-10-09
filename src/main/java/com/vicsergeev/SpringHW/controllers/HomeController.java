package com.vicsergeev.SpringHW.controllers;

/**
 * Created by Victor 09.10.2025
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the User Management API. Use /users to access user endpoints.");
    }
}