package com.taskflow.vacation.management.employee.service;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.dto.CreateEmployeeRequest;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import com.taskflow.vacation.management.employee.dto.UpdateEmployeeRequest;
import com.taskflow.vacation.management.employee.mapper.EmployeeMapper;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.employee.repository.EmployeeSpec;
import com.taskflow.vacation.management.employee.validation.EmployeeValidator;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.service.UserService;
import com.taskflow.vacation.management.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
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
        log.info("Creating employee. name={}, email={}, role={}", request.fullName(), request.email(), request.role());

        employeeValidator.validateEmail(request.email(), null);
        employeeValidator.validateBusinessRules(request.role(), request.managerId());
        Employee manager = employeeValidator.getManager(request.managerId());

        User user = userService.create(request.username(), request.password(), request.role());
        Employee employee = employeeRepository.save(new Employee(request.fullName(), request.email(), request.role(), manager, user));

        log.info("Employee created successfully. id={}, name={}, role={}", employee.getId(), employee.getFullName(), employee.getRole());

        return employeeMapper.toResponse(employee);
    }

    @Override
    public void update(UUID id, UpdateEmployeeRequest request) {
        log.info("Updating employee. id={}, name={}", id, request.fullName());

        Employee employee = findEmployeeById(id);

        employeeValidator.validateEmail(request.email(), id);
        employeeValidator.validateBusinessRulesOnUpdate(employee, request.managerId());
        Employee manager = employeeValidator.getManager(request.managerId());

        userService.updateUsername(employee.getUser().getId(), request.username());

        employee.setFullName(request.fullName());
        employee.setEmail(request.email());
        employee.setManager(manager);

        log.info("Employee updated successfully. id={}, name={}", id, request.fullName());
    }

    @Override
    public EmployeeResponse findById(UUID id) {
        log.debug("Fetching employee. id={}", id);
        EmployeeResponse response = employeeMapper.toResponse(findEmployeeById(id));
        log.debug("Employee fetched successfully. id={}", id);
        return response;
    }

    @Override
    public Page<EmployeeResponse> findAll(UUID managerId, String fullName, String email, Role role, Pageable pageable) {
        log.debug("Listing employees. filter=managerId:{}, fullName:{}, email:{}, role:{}, page={}",
                managerId, fullName, email, role, pageable.getPageNumber());

        Specification<Employee> spec = Specification.allOf(
                EmployeeSpec.hasManagerId(managerId),
                EmployeeSpec.hasFullNameLike(fullName),
                EmployeeSpec.hasEmailLike(email),
                EmployeeSpec.hasRole(role)
        );
        return employeeRepository.findAll(spec, pageable).map(employeeMapper::toResponse);
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting employee. id={}", id);

        Employee employee = findEmployeeById(id);

        if (employeeRepository.existsByManagerId(id)) {
            throw new BadRequestException("employee.manager.has_collaborators");
        }

        employee.delete();
        employee.getUser().deactivate();

        log.info("Employee deleted successfully. id={}, name={}", id, employee.getFullName());
    }

    private Employee findEmployeeById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", id));
    }
}
