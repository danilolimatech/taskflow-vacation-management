package com.taskflow.vacation.management.employee.dto;

import com.taskflow.vacation.management.user.entity.Role;

import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String fullName,
        String email,
        UUID managerId,
        String username,
        Role role
) {
}