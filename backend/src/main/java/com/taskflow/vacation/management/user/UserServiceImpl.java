package com.taskflow.vacation.management.user;

import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(String username, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("user.username.conflict", username);
        }
        return userRepository.save(new User(username, passwordEncoder.encode(password), role));
    }
}
