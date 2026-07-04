package com.taskflow.vacation.management.employee.controller;

import com.taskflow.vacation.management.employee.dto.*;
import com.taskflow.vacation.management.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse create(@Valid @RequestBody CreateEmployeeRequest request) {
        return employeeService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@PathVariable UUID id, @Valid @RequestBody UpdateEmployeeRequest request) {
        employeeService.update(id, request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponse findById(@PathVariable UUID id) {
        return employeeService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<EmployeeResponse> findAll(
            @RequestParam(required = false) UUID managerId,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 20, sort = "fullName") Pageable pageable
    ) {
        return employeeService.findAll(managerId, fullName, email, pageable);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id) {
        employeeService.delete(id);
    }
}
