package com.taskflow.vacation.management.employee.service;

import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import com.taskflow.vacation.management.employee.mapper.EmployeeMapper;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.employee.validation.EmployeeValidator;
import com.taskflow.vacation.management.user.UserService;
import com.taskflow.vacation.management.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final EmployeeValidator employeeValidator;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeResponse create(CreateEmployeeRequest request) {
        employeeValidator.validateEmail(request.email(), null);
        employeeValidator.validateBusinessRules(request.role(), request.managerId());
        Employee manager = employeeValidator.getManager(request.managerId());

        User user = userService.create(request.username(), request.password(), request.role());
        Employee employee = employeeRepository.save(new Employee(request.fullName(), request.email(), manager, user));

        return employeeMapper.toResponse(employee);
    }
}
