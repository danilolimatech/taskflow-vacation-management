package com.taskflow.vacation.management.employee.service;

import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import com.taskflow.vacation.management.employee.dto.UpdateEmployeeRequest;
import com.taskflow.vacation.management.user.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse create(CreateEmployeeRequest request);
    void update(UUID id, UpdateEmployeeRequest request);
    EmployeeResponse findById(UUID id);
    Page<EmployeeResponse> findAll(UUID managerId, String fullName, String email, Role role, Pageable pageable);
    void delete(UUID id);
}
