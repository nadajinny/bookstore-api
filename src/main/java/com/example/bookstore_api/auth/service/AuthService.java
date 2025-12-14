package com.example.bookstore_api.auth.service;

import com.example.bookstore_api.auth.dto.LoginRequest;
import com.example.bookstore_api.auth.dto.LoginResponse;
import com.example.bookstore_api.auth.dto.RefreshTokenRequest;
import com.example.bookstore_api.auth.security.JwtTokenProvider;
import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.user.entity.User;
import com.example.bookstore_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(AuthException::invalidCredentials);

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw AuthException.invalidCredentials();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenValiditySeconds())
                .refreshTokenExpiresIn(jwtTokenProvider.getRefreshTokenValiditySeconds())
                .build();
    }

    @Transactional(readOnly = true)
    public LoginResponse refresh(RefreshTokenRequest request) {
        if (!jwtTokenProvider.isRefreshToken(request.refreshToken())) {
            throw AuthException.invalidToken("리프레시 토큰이 아닙니다.");
        }

        jwtTokenProvider.validateToken(request.refreshToken());
        String email = jwtTokenProvider.getEmail(request.refreshToken());
        User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> AuthException.invalidToken("사용자를 찾을 수 없습니다."));

        String newAccessToken = jwtTokenProvider.generateAccessToken(user.getEmail());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .accessTokenExpiresIn(jwtTokenProvider.getAccessTokenValiditySeconds())
                .refreshTokenExpiresIn(jwtTokenProvider.getRefreshTokenValiditySeconds())
                .build();
    }

    @Transactional
    public void logout(String authorizationHeader) {
        // 토큰 무효화나 블랙리스트 처리가 필요한 경우 이곳에 구현합니다.
    }
}
