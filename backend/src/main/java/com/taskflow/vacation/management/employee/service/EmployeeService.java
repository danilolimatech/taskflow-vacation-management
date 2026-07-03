package com.taskflow.vacation.management.employee.service;

import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse create(CreateEmployeeRequest request);

}