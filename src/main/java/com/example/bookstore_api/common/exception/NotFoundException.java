package com.example.bookstore_api.common.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(ErrorCode.RESOURCE_NOT_FOUND, message);
    }

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static NotFoundException userNotFound(Long userId) {
        return new NotFoundException(ErrorCode.USER_NOT_FOUND, "사용자를 찾을 수 없습니다. id=" + userId);
    }

    public static NotFoundException bookNotFound(Long bookId) {
        return new NotFoundException(ErrorCode.BOOK_NOT_FOUND, "도서를 찾을 수 없습니다. id=" + bookId);
    }
}
