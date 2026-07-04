package com.taskflow.vacation.management.user.validation;

import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateUsername(String username, UUID userId) {
        if (userRepository.existsByUsernameAndIdNot(username, userId)) {
            throw new ConflictException("user.username.conflict", username);
        }
    }
}
