package com.example.bookstore_api.user.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserActionResponse {

    private final String message;
    private final LocalDateTime occurredAt;
}
