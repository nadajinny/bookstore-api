package com.example.bookstore_api.book.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookActionResponse {

    private Long bookId;
    private String message;
    private LocalDateTime occurredAt;
}
