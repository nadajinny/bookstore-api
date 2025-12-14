package com.example.bookstore_api.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Void> root() {
        return ResponseEntity.status(302)
                .location(URI.create("/swagger-ui.html"))
                .build();
    }
}
