package com.example.bookstore_api.health;

import com.example.bookstore_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final UserRepository userRepository;

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "time", LocalDateTime.now()
        );
    }

    @GetMapping("/health/db")
    public String dbHealth() {
        userRepository.count();
        return "DB OK";
    }
}
