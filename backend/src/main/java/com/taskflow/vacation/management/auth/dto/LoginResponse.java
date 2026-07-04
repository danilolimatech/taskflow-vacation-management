package com.taskflow.vacation.management.auth.dto;

import com.taskflow.vacation.management.user.entity.Role;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String username,
        Role role
) {}
