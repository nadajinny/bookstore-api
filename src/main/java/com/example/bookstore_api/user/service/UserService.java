package com.example.bookstore_api.user.service;

import com.example.bookstore_api.common.exception.NotFoundException;
import com.example.bookstore_api.user.entity.User;
import com.example.bookstore_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getActiveUser(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> NotFoundException.userNotFound(userId));
    }
}
