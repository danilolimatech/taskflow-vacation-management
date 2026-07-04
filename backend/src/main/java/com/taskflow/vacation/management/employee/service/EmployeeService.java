package com.taskflow.vacation.management.employee.service;

import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import com.taskflow.vacation.management.employee.dto.UpdateEmployeeRequest;

import java.util.UUID;

public interface EmployeeService {
    EmployeeResponse create(CreateEmployeeRequest request);
    void update(UUID id, UpdateEmployeeRequest request);
}