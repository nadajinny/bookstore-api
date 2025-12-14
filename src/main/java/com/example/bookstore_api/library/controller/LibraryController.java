package com.example.bookstore_api.library.controller;

import com.example.bookstore_api.auth.security.CustomUserDetails;
import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.common.response.PageResponse;
import com.example.bookstore_api.library.dto.LibraryActionResponse;
import com.example.bookstore_api.library.dto.LibraryAddRequest;
import com.example.bookstore_api.library.dto.LibraryEntryResponse;
import com.example.bookstore_api.library.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
@Tag(name = "Library", description = "내 서재 API")
public class LibraryController {

    private final LibraryService libraryService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "서재에 도서 추가")
    public ApiResponse<LibraryActionResponse> addBook(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody LibraryAddRequest request
    ) {
        return ApiResponse.ok(libraryService.addBook(requireUserId(userDetails), request));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "내 서재 목록 조회")
    public ApiResponse<PageResponse<LibraryEntryResponse>> getLibrary(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "addedAt,desc") String sort
    ) {
        return ApiResponse.ok(libraryService.getLibrary(requireUserId(userDetails), page, size, sort));
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "서재에서 도서 삭제")
    public ApiResponse<LibraryActionResponse> removeBook(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long bookId
    ) {
        return ApiResponse.ok(libraryService.removeBook(requireUserId(userDetails), bookId));
    }

    private Long requireUserId(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw AuthException.unauthorized("로그인이 필요합니다.");
        }
        return userDetails.getId();
    }
}
