package com.example.bookstore_api.book.service;

import com.example.bookstore_api.book.dto.BookActionResponse;
import com.example.bookstore_api.book.dto.BookArrayUpdateRequest;
import com.example.bookstore_api.book.dto.BookCreateRequest;
import com.example.bookstore_api.book.dto.BookDetailResponse;
import com.example.bookstore_api.book.dto.BookSummaryResponse;
import com.example.bookstore_api.book.dto.BookUpdateRequest;
import com.example.bookstore_api.common.response.PageResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    public BookActionResponse createBook(BookCreateRequest request) {
        return BookActionResponse.builder()
            .bookId(1001L)
            .message("도서가 등록되었습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public PageResponse<BookSummaryResponse> getBooks(
        int page,
        int size,
        String sort,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        String category,
        String author,
        String keyword
    ) {
        List<BookSummaryResponse> content = List.of(
            BookSummaryResponse.builder()
                .bookId(1L)
                .title("객체지향의 사실과 오해")
                .price(BigDecimal.valueOf(18000))
                .stock(12)
                .averageRating(4.8)
                .reviewCount(125)
                .authors(List.of("조영호"))
                .categories(List.of("소프트웨어", "개발"))
                .createdAt(LocalDateTime.now().minusDays(10))
                .build(),
            BookSummaryResponse.builder()
                .bookId(2L)
                .title("클린 코드")
                .price(BigDecimal.valueOf(25000))
                .stock(8)
                .averageRating(4.6)
                .reviewCount(210)
                .authors(List.of("로버트 C. 마틴"))
                .categories(List.of("소프트웨어", "베스트셀러"))
                .createdAt(LocalDateTime.now().minusDays(30))
                .build()
        );
        return new PageResponse<>(content, page, size, content.size(), 1);
    }

    public BookDetailResponse getBookDetail(Long bookId) {
        return BookDetailResponse.builder()
            .bookId(bookId)
            .title("샘플 도서")
            .description("샘플 도서 상세 설명")
            .isbn("9781234567890")
            .price(BigDecimal.valueOf(15000))
            .stock(5)
            .averageRating(4.5)
            .reviewCount(50)
            .authors(List.of("샘플 저자", "공동 저자"))
            .categories(List.of("기술", "IT"))
            .createdAt(LocalDateTime.now().minusMonths(1))
            .updatedAt(LocalDateTime.now())
            .build();
    }

    public BookActionResponse updateBook(Long bookId, BookUpdateRequest request) {
        return BookActionResponse.builder()
            .bookId(bookId)
            .message("도서 정보가 수정되었습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public BookActionResponse updateBookArrays(Long bookId, BookArrayUpdateRequest request) {
        return BookActionResponse.builder()
            .bookId(bookId)
            .message("저자/카테고리 정보가 업데이트되었습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }

    public BookActionResponse deleteBook(Long bookId) {
        return BookActionResponse.builder()
            .bookId(bookId)
            .message("도서가 삭제되었습니다.")
            .occurredAt(LocalDateTime.now())
            .build();
    }
}
