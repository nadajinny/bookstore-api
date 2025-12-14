package com.example.bookstore_api.book.controller;

import com.example.bookstore_api.book.dto.BookActionResponse;
import com.example.bookstore_api.book.dto.BookArrayUpdateRequest;
import com.example.bookstore_api.book.dto.BookCreateRequest;
import com.example.bookstore_api.book.dto.BookDetailResponse;
import com.example.bookstore_api.book.dto.BookSummaryResponse;
import com.example.bookstore_api.book.dto.BookUpdateRequest;
import com.example.bookstore_api.book.service.BookService;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "도서 관리 API")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "도서 등록", description = "판매자 또는 관리자만 새로운 도서를 등록할 수 있습니다.")
    public ApiResponse<BookActionResponse> createBook(@Valid @RequestBody BookCreateRequest request) {
        return ApiResponse.ok(bookService.createBook(request));
    }

    @GetMapping
    @Operation(summary = "도서 목록 조회", description = "페이징/정렬/검색/필터링이 가능한 도서 목록 API")
    public ApiResponse<PageResponse<BookSummaryResponse>> getBooks(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt,desc") String sort,
        @RequestParam(required = false) @Parameter(description = "최소 가격") BigDecimal minPrice,
        @RequestParam(required = false) @Parameter(description = "최대 가격") BigDecimal maxPrice,
        @RequestParam(required = false) @Parameter(description = "카테고리 필터") String category,
        @RequestParam(required = false) @Parameter(description = "저자 필터") String author,
        @RequestParam(required = false) @Parameter(description = "키워드 검색") String keyword
    ) {
        return ApiResponse.ok(bookService.getBooks(page, size, sort, minPrice, maxPrice, category, author, keyword));
    }

    @GetMapping("/{bookId}")
    @Operation(summary = "도서 상세 조회", description = "평균 평점과 리뷰 개수를 포함한 도서 상세 정보")
    public ApiResponse<BookDetailResponse> getBookDetail(@PathVariable Long bookId) {
        return ApiResponse.ok(bookService.getBookDetail(bookId));
    }

    @PatchMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @Operation(summary = "도서 수정", description = "부분 수정이 가능하며 수정 시각을 반환합니다.")
    public ApiResponse<BookActionResponse> updateBook(
        @PathVariable Long bookId,
        @Valid @RequestBody BookUpdateRequest request
    ) {
        return ApiResponse.ok(bookService.updateBook(bookId, request));
    }

    @PatchMapping("/{bookId}/arrays")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @Operation(summary = "저자/카테고리 배열 관리", description = "저자/카테고리 배열을 전체 교체하거나 추가/삭제할 수 있습니다.")
    public ApiResponse<BookActionResponse> updateBookArrays(
        @PathVariable Long bookId,
        @Valid @RequestBody BookArrayUpdateRequest request
    ) {
        return ApiResponse.ok(bookService.updateBookArrays(bookId, request));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('SELLER','ADMIN')")
    @Operation(summary = "도서 삭제", description = "판매자 또는 관리자만 도서를 삭제할 수 있습니다.")
    public ApiResponse<BookActionResponse> deleteBook(@PathVariable Long bookId) {
        return ApiResponse.ok(bookService.deleteBook(bookId));
    }
}
