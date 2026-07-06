package com.taskflow.vacation.management.user.validation;

import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserValidator userValidator;

    @Test
    void validateUsername_usernameNotTaken_noException() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsByUsernameAndIdNot("alice", userId)).thenReturn(false);

        assertThatCode(() -> userValidator.validateUsername("alice", userId))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUsername_usernameTakenByAnotherUser_throwsConflict() {
        UUID userId = UUID.randomUUID();
        when(userRepository.existsByUsernameAndIdNot("alice", userId)).thenReturn(true);

        assertThatThrownBy(() -> userValidator.validateUsername("alice", userId))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("user.username.conflict");
    }
}
