package com.example.bookstore_api.book.controller;

import com.example.bookstore_api.book.dto.BookRequest;
import com.example.bookstore_api.book.dto.BookResponse;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Books", description = "도서 관리 API")
public class BookController {

    @PostMapping("/books")
    @Operation(summary = "도서 등록")
    public ApiResponse<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        BookResponse response = BookResponse.builder()
            .bookId(1L)
            .title(request.getTitle())
            .author(request.getAuthor())
            .description(request.getDescription())
            .price(request.getPrice())
            .available(true)
            .build();
        return ApiResponse.ok(response);
    }

    @PutMapping("/books/{bookId}")
    @Operation(summary = "도서 수정")
    public ApiResponse<BookResponse> updateBook(
        @PathVariable Long bookId,
        @Valid @RequestBody BookRequest request
    ) {
        BookResponse response = BookResponse.builder()
            .bookId(bookId)
            .title(request.getTitle())
            .author(request.getAuthor())
            .description(request.getDescription())
            .price(request.getPrice())
            .available(true)
            .build();
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/books/{bookId}")
    @Operation(summary = "도서 삭제")
    public ApiResponse<Void> deleteBook(@PathVariable Long bookId) {
        return ApiResponse.ok(null);
    }

    @GetMapping("/public/books")
    @Operation(summary = "도서 전체 조회")
    public ApiResponse<PageResponse<BookResponse>> getBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        List<BookResponse> content = List.of(
            BookResponse.builder()
                .bookId(1L)
                .title("객체지향의 사실과 오해")
                .author("조영호")
                .description("객체지향 개념 설명")
                .price(BigDecimal.valueOf(18000))
                .available(true)
                .build(),
            BookResponse.builder()
                .bookId(2L)
                .title("클린 코드")
                .author("로버트 C. 마틴")
                .description("좋은 코드 작성 가이드")
                .price(BigDecimal.valueOf(25000))
                .available(true)
                .build()
        );
        PageResponse<BookResponse> response = new PageResponse<>(content, page, size, content.size(), 1);
        return ApiResponse.ok(response);
    }

    @GetMapping("/public/books/{bookId}")
    @Operation(summary = "도서 상세 조회")
    public ApiResponse<BookResponse> getBookDetail(@PathVariable Long bookId) {
        BookResponse response = BookResponse.builder()
            .bookId(bookId)
            .title("샘플 도서")
            .author("저자")
            .description("도서 상세 설명")
            .price(BigDecimal.valueOf(15000))
            .available(true)
            .build();
        return ApiResponse.ok(response);
    }
}
