package com.example.bookstore_api.library.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LibraryEntryResponse {

    private Long bookId;
    private String title;
    private String author;
    private LocalDateTime addedAt;
}
