package com.example.bookstore_api.library.service;

import com.example.bookstore_api.common.response.PageResponse;
import com.example.bookstore_api.library.dto.LibraryActionResponse;
import com.example.bookstore_api.library.dto.LibraryAddRequest;
import com.example.bookstore_api.library.dto.LibraryEntryResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    public LibraryActionResponse addBook(Long userId, LibraryAddRequest request) {
        return LibraryActionResponse.builder()
            .bookId(request.getBookId())
            .message("서재에 도서를 추가했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public PageResponse<LibraryEntryResponse> getLibrary(Long userId, int page, int size, String sort) {
        List<LibraryEntryResponse> content = List.of(
            LibraryEntryResponse.builder()
                .bookId(1L)
                .title("객체지향의 사실과 오해")
                .author("조영호")
                .addedAt(LocalDateTime.now().minusDays(5))
                .build(),
            LibraryEntryResponse.builder()
                .bookId(2L)
                .title("클린 아키텍처")
                .author("로버트 C. 마틴")
                .addedAt(LocalDateTime.now().minusDays(2))
                .build()
        );
        return new PageResponse<>(content, page, size, content.size(), 1);
    }

    public LibraryActionResponse removeBook(Long userId, Long bookId) {
        return LibraryActionResponse.builder()
            .bookId(bookId)
            .message("서재에서 도서를 삭제했습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }
}
