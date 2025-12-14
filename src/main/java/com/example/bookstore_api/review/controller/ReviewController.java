package com.example.bookstore_api.review.controller;

import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.review.dto.ReviewRequest;
import com.example.bookstore_api.review.dto.ReviewResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Reviews", description = "리뷰 API")
public class ReviewController {

    @PostMapping("/books/{bookId}/reviews")
    @Operation(summary = "리뷰 작성")
    public ApiResponse<ReviewResponse> createReview(
        @PathVariable Long bookId,
        @Valid @RequestBody ReviewRequest request
    ) {
        return ApiResponse.ok(buildResponse(1L, bookId, request));
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 상세 조회")
    public ApiResponse<ReviewResponse> getReview(@PathVariable Long reviewId) {
        ReviewResponse response = ReviewResponse.builder()
            .reviewId(reviewId)
            .bookId(1L)
            .bookTitle("샘플 도서")
            .rating(5)
            .content("훌륭한 책입니다.")
            .createdAt(LocalDateTime.now().minusDays(1))
            .build();
        return ApiResponse.ok(response);
    }

    @PatchMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정")
    public ApiResponse<ReviewResponse> updateReview(
        @PathVariable Long reviewId,
        @Valid @RequestBody ReviewRequest request
    ) {
        ReviewResponse response = buildResponse(reviewId, 1L, request);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제")
    public ApiResponse<Void> deleteReview(@PathVariable Long reviewId) {
        return ApiResponse.ok(null);
    }

    @GetMapping("/reviews/me")
    @Operation(summary = "내 리뷰 조회")
    public ApiResponse<List<ReviewResponse>> getMyReviews() {
        List<ReviewResponse> responses = List.of(
            buildResponse(1L, 1L, ReviewRequest.builder().rating(5).content("재미있어요").build()),
            buildResponse(2L, 2L, ReviewRequest.builder().rating(4).content("추천합니다").build())
        );
        return ApiResponse.ok(responses);
    }

    private ReviewResponse buildResponse(Long reviewId, Long bookId, ReviewRequest request) {
        return ReviewResponse.builder()
            .reviewId(reviewId)
            .bookId(bookId)
            .bookTitle("샘플 도서")
            .rating(request.getRating())
            .content(request.getContent())
            .createdAt(LocalDateTime.now())
            .build();
    }
}
