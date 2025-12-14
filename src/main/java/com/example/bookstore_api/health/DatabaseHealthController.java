package com.example.bookstore_api.health;

import com.example.bookstore_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DatabaseHealthController {

    private final UserRepository userRepository;

    @GetMapping("/health/db")
    public String dbHealth() {
        userRepository.count();
        return "DB OK";
    }
}
