package com.taskflow.vacation.management.employee.controller;

import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import com.taskflow.vacation.management.employee.dto.UpdateEmployeeRequest;
import com.taskflow.vacation.management.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@Valid @RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateEmployeeRequest request
    ) {
        employeeService.update(id, request);
    }
}
