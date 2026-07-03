package com.taskflow.vacation.management.employee.validation;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.user.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EmployeeValidator {

    private final EmployeeRepository employeeRepository;

    public void validateEmail(String email, UUID employeeId) {
        if (employeeRepository.existsByEmailAndIdNot(email, employeeId)) {
            throw new ConflictException("employee.email.conflict", email);
        }
    }

    public void validateBusinessRules(Role role, UUID managerId) {
        if (role == Role.COLLABORATOR && managerId == null) {
            throw new BadRequestException("employee.collaborator.manager.required");
        }
    }

    public Employee getManager(UUID managerId) {
        if (managerId == null) return null;
        return employeeRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("employee.manager.not_found", managerId));
    }
}
