package com.taskflow.vacation.management.user;

import com.taskflow.vacation.management.user.dto.ChangePasswordRequest;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;

import java.util.UUID;

public interface UserService {
    User create(String username, String password, Role role);
    void changePassword(UUID userId, ChangePasswordRequest request);
    void updateUsername(UUID userId, String username);
}
