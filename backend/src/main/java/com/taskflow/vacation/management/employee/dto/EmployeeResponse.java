package com.taskflow.vacation.management.employee.dto;

import com.taskflow.vacation.management.user.entity.Role;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String fullName,
        String email,
        UUID managerId,
        String managerName,
        String username,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}