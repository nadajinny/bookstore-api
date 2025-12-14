package com.example.bookstore_api.review.controller;

import com.example.bookstore_api.auth.security.CustomUserDetails;
import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.review.dto.ReviewCreateRequest;
import com.example.bookstore_api.review.dto.ReviewDetailResponse;
import com.example.bookstore_api.review.dto.ReviewLikeResponse;
import com.example.bookstore_api.review.dto.ReviewListResponse;
import com.example.bookstore_api.review.dto.ReviewSummaryResponse;
import com.example.bookstore_api.review.dto.ReviewUpdateRequest;
import com.example.bookstore_api.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "리뷰 작성", description = "로그인한 사용자가 도서에 리뷰를 작성합니다.")
    public ApiResponse<ReviewDetailResponse> createReview(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody ReviewCreateRequest request
    ) {
        return ApiResponse.ok(reviewService.createReview(requireUserId(userDetails), request));
    }

    @GetMapping
    @Operation(summary = "특정 도서 리뷰 목록 조회", description = "정렬과 필터링을 지원하며 평균 평점을 제공합니다.")
    public ApiResponse<ReviewListResponse> getBookReviews(
        @RequestParam Long bookId,
        @RequestParam(defaultValue = "createdAt,desc") @Parameter(description = "정렬 조건 (rating,createdAt,likes)") String sort,
        @RequestParam(required = false) @Parameter(description = "필터 조건 (e.g. rating:5)") String filter,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return ApiResponse.ok(reviewService.getBookReviews(bookId, sort, filter, userId));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "내 리뷰 목록 조회")
    public ApiResponse<List<ReviewSummaryResponse>> getMyReviews(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(reviewService.getMyReviews(requireUserId(userDetails)));
    }

    @PatchMapping("/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "리뷰 수정", description = "본인이 작성한 리뷰만 수정할 수 있습니다.")
    public ApiResponse<ReviewDetailResponse> updateReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody ReviewUpdateRequest request
    ) {
        return ApiResponse.ok(reviewService.updateReview(requireUserId(userDetails), reviewId, request));
    }

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "리뷰 삭제", description = "Soft Delete 방식으로 처리합니다.")
    public ApiResponse<Void> deleteReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        reviewService.deleteReview(requireUserId(userDetails), reviewId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/{reviewId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "리뷰 좋아요", description = "좋아요 수가 증가합니다.")
    public ApiResponse<ReviewLikeResponse> likeReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(reviewService.likeReview(requireUserId(userDetails), reviewId));
    }

    @DeleteMapping("/{reviewId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "리뷰 좋아요 취소", description = "좋아요 수가 감소합니다.")
    public ApiResponse<ReviewLikeResponse> unlikeReview(
        @PathVariable Long reviewId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(reviewService.unlikeReview(requireUserId(userDetails), reviewId));
    }

    @GetMapping("/liked")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "좋아요한 리뷰 조회")
    public ApiResponse<List<ReviewSummaryResponse>> getLikedReviews(
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(reviewService.getLikedReviews(requireUserId(userDetails)));
    }

    private Long requireUserId(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw AuthException.unauthorized("로그인이 필요합니다.");
        }
        return userDetails.getId();
    }
}
