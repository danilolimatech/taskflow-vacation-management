package com.taskflow.vacation.management.user.service;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.user.dto.ChangePasswordRequest;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;
import com.taskflow.vacation.management.user.repository.UserRepository;
import com.taskflow.vacation.management.user.validation.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserValidator userValidator;

    @InjectMocks
    UserServiceImpl userService;

    private User buildUser(UUID id) {
        User user = new User("alice", "encoded-pw", Role.COLLABORATOR);
        try {
            var f = User.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(user, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Test
    void create_usernameNotTaken_savesAndReturnsUser() {
        when(userRepository.existsByUsername("alice")).thenReturn(false);
        when(passwordEncoder.encode("secret123")).thenReturn("encoded");
        User saved = new User("alice", "encoded", Role.COLLABORATOR);
        when(userRepository.save(any(User.class))).thenReturn(saved);

        User result = userService.create("alice", "secret123", Role.COLLABORATOR);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void create_usernameTaken_throwsConflict() {
        when(userRepository.existsByUsername("alice")).thenReturn(true);

        assertThatThrownBy(() -> userService.create("alice", "secret123", Role.COLLABORATOR))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("user.username.conflict");

        verify(userRepository, never()).save(any());
    }

    @Test
    void changePassword_correctCurrentPassword_changesPassword() {
        UUID id = UUID.randomUUID();
        User user = buildUser(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("current123", "encoded-pw")).thenReturn(true);
        when(passwordEncoder.encode("newPassword1")).thenReturn("new-encoded");

        assertThatCode(() -> userService.changePassword(id, new ChangePasswordRequest("current123", "newPassword1")))
                .doesNotThrowAnyException();

        verify(passwordEncoder).encode("newPassword1");
    }

    @Test
    void changePassword_wrongCurrentPassword_throwsBadRequest() {
        UUID id = UUID.randomUUID();
        User user = buildUser(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded-pw")).thenReturn(false);

        assertThatThrownBy(() -> userService.changePassword(id, new ChangePasswordRequest("wrong", "newPassword1")))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("user.password.invalid");
    }

    @Test
    void changePassword_userNotFound_throwsResourceNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.changePassword(id, new ChangePasswordRequest("current123", "newPassword1")))
                .isInstanceOf(ResourceNotFoundException.class)
                .extracting("messageKey")
                .isEqualTo("user.not_found");
    }

    @Test
    void updateUsername_delegatesToValidatorAndUpdatesUser() {
        UUID id = UUID.randomUUID();
        User user = buildUser(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        doNothing().when(userValidator).validateUsername("newAlice", id);

        assertThatCode(() -> userService.updateUsername(id, "newAlice"))
                .doesNotThrowAnyException();

        verify(userValidator).validateUsername("newAlice", id);
    }
}
