package com.example.bookstore_api.user.service;

import com.example.bookstore_api.common.exception.AuthException;
import com.example.bookstore_api.common.exception.BusinessException;
import com.example.bookstore_api.common.exception.ErrorCode;
import com.example.bookstore_api.common.exception.NotFoundException;
import com.example.bookstore_api.user.dto.UserProfileResponse;
import com.example.bookstore_api.user.dto.UserRegisterRequest;
import com.example.bookstore_api.user.dto.UserUpdateRequest;
import com.example.bookstore_api.user.entity.User;
import com.example.bookstore_api.user.entity.UserRole;
import com.example.bookstore_api.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserProfileResponse register(UserRegisterRequest request) {
        ensureEmailUnique(request.getEmail(), null);
        UserRole role = Optional.ofNullable(request.getRole()).orElse(UserRole.USER);

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .passwordHash(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .build();

        User saved = userRepository.save(user);
        return UserProfileResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public User getActiveUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> NotFoundException.userNotFound(userId));
    }

    @Transactional(readOnly = true)
    public User getActiveUser(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
            .orElseThrow(AuthException::invalidCredentials);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(Long userId) {
        return UserProfileResponse.from(getActiveUser(userId));
    }

    @Transactional
    public UserProfileResponse updateProfile(Long userId, UserUpdateRequest request) {
        User user = getActiveUser(userId);

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            ensureEmailUnique(request.getEmail(), userId);
            user.changeEmail(request.getEmail());
        }

        if (request.getName() != null) {
            user.changeName(request.getName());
        }

        if (request.getPassword() != null) {
            user.changePassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getRole() != null) {
            user.changeRole(request.getRole());
        }

        return UserProfileResponse.from(user);
    }

    @Transactional
    public void softDelete(Long userId) {
        User user = getActiveUser(userId);
        user.markDeleted();
    }

    private void ensureEmailUnique(String email, Long excludeUserId) {
        userRepository.findByEmail(email).ifPresent(existing -> {
            if (excludeUserId == null || !existing.getId().equals(excludeUserId)) {
                throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS, "이미 등록된 이메일입니다.");
            }
        });
    }
}
