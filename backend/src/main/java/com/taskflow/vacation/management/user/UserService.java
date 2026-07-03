package com.taskflow.vacation.management.user;

import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;

public interface UserService {
    User create(String username, String password, Role role);
}
