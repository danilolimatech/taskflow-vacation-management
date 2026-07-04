package com.taskflow.vacation.management.employee.service;

import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import com.taskflow.vacation.management.employee.dto.UpdateEmployeeRequest;
import com.taskflow.vacation.management.employee.mapper.EmployeeMapper;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.employee.repository.EmployeeSpec;
import com.taskflow.vacation.management.employee.validation.EmployeeValidator;
import com.taskflow.vacation.management.user.service.UserService;
import com.taskflow.vacation.management.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        Employee employee = employeeRepository.save(new Employee(request.fullName(), request.email(), request.role(), manager, user));

        return employeeMapper.toResponse(employee);
    }

    @Override
    public void update(UUID id, UpdateEmployeeRequest request) {
        Employee employee = findActiveById(id);

        employeeValidator.validateEmail(request.email(), id);
        employeeValidator.validateBusinessRulesOnUpdate(employee, request.managerId());
        Employee manager = employeeValidator.getManager(request.managerId());

        userService.updateUsername(employee.getUser().getId(), request.username());

        employee.setFullName(request.fullName());
        employee.setEmail(request.email());
        employee.setManager(manager);
    }

    @Override
    public EmployeeResponse findById(UUID id) {
        return employeeMapper.toResponse(findActiveById(id));
    }

    @Override
    public Page<EmployeeResponse> findAll(UUID managerId, String fullName, String email, Pageable pageable) {
        Specification<Employee> spec = Specification.allOf(
                EmployeeSpec.hasManagerId(managerId),
                EmployeeSpec.hasFullNameLike(fullName),
                EmployeeSpec.hasEmailLike(email)
        );
        return employeeRepository.findAll(spec, pageable).map(employeeMapper::toResponse);
    }

    @Override
    public void delete(UUID id) {
        Employee employee = findActiveById(id);
        employee.delete();
        employee.getUser().deactivate();
    }

    private Employee findActiveById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", id));
    }
}
