package com.taskflow.vacation.management.user.service;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.user.dto.ChangePasswordRequest;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;
import com.taskflow.vacation.management.user.repository.UserRepository;
import com.taskflow.vacation.management.user.validation.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Override
    public User create(String username, String password, Role role) {
        log.info("Creating user. username={}, role={}", username, role);

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException("user.username.conflict", username);
        }

        User user = userRepository.save(new User(username, passwordEncoder.encode(password), role));

        log.info("User created successfully. id={}, username={}, role={}", user.getId(), username, role);

        return user;
    }

    @Override
    public void changePassword(UUID userId, ChangePasswordRequest request) {
        log.info("Changing password. userId={}", userId);

        User user = findById(userId);
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new BadRequestException("user.password.invalid");
        }
        user.changePassword(passwordEncoder.encode(request.newPassword()));

        log.info("Password changed successfully. userId={}", userId);
    }

    @Override
    public void updateUsername(UUID userId, String username) {
        log.info("Updating username. userId={}, newUsername={}", userId, username);

        userValidator.validateUsername(username, userId);
        findById(userId).changeUsername(username);

        log.info("Username updated successfully. userId={}, newUsername={}", userId, username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public UserDetails loadUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }

    private User findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user.not_found", userId));
    }
}
