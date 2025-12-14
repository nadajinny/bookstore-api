package com.example.bookstore_api.comment.controller;

import com.example.bookstore_api.auth.security.CustomUserDetails;
import com.example.bookstore_api.comment.dto.CommentCreateRequest;
import com.example.bookstore_api.comment.dto.CommentLikeResponse;
import com.example.bookstore_api.comment.dto.CommentResponse;
import com.example.bookstore_api.comment.dto.CommentUpdateRequest;
import com.example.bookstore_api.comment.service.CommentService;
import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.common.response.ApiResponse;
import com.example.bookstore_api.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 작성", description = "특정 리뷰에 댓글을 작성합니다.")
    public ApiResponse<CommentResponse> createComment(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody CommentCreateRequest request
    ) {
        return ApiResponse.ok(commentService.createComment(requireUserId(userDetails), request));
    }

    @GetMapping
    @Operation(summary = "댓글 목록 조회", description = "리뷰 ID 기준으로 정렬과 페이징을 지원합니다.")
    public ApiResponse<PageResponse<CommentResponse>> getComments(
        @RequestParam Long reviewId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "createdAt,desc") String sort,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails != null ? userDetails.getId() : null;
        return ApiResponse.ok(commentService.getComments(reviewId, page, size, sort, userId));
    }

    @PatchMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 수정", description = "본인이 작성한 댓글만 수정할 수 있습니다.")
    public ApiResponse<CommentResponse> updateComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @Valid @RequestBody CommentUpdateRequest request
    ) {
        return ApiResponse.ok(commentService.updateComment(requireUserId(userDetails), commentId, request));
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 삭제")
    public ApiResponse<Void> deleteComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.deleteComment(requireUserId(userDetails), commentId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/{commentId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 좋아요")
    public ApiResponse<CommentLikeResponse> likeComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(commentService.likeComment(requireUserId(userDetails), commentId));
    }

    @DeleteMapping("/{commentId}/like")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "댓글 좋아요 취소")
    public ApiResponse<CommentLikeResponse> unlikeComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(commentService.unlikeComment(requireUserId(userDetails), commentId));
    }

    private Long requireUserId(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw AuthException.unauthorized("로그인이 필요합니다.");
        }
        return userDetails.getId();
    }
}
