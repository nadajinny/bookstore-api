package com.example.bookstore_api.common.exception;

public class AuthException extends BusinessException {

    public AuthException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static AuthException unauthorized(String message) {
        return new AuthException(ErrorCode.UNAUTHENTICATED, message);
    }

    public static AuthException invalidCredentials() {
        return new AuthException(ErrorCode.INVALID_CREDENTIALS, "이메일 혹은 비밀번호가 올바르지 않습니다.");
    }

    public static AuthException tokenExpired() {
        return new AuthException(ErrorCode.TOKEN_EXPIRED, "토큰이 만료되었습니다.");
    }
}
