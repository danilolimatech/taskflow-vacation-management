package com.taskflow.vacation.management.vacation.service;

import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.user.entity.User;
import com.taskflow.vacation.management.vacation.dto.VacationRequest;
import com.taskflow.vacation.management.vacation.dto.VacationResponse;
import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import com.taskflow.vacation.management.vacation.mapper.VacationMapper;
import com.taskflow.vacation.management.vacation.repository.VacationRepository;
import com.taskflow.vacation.management.vacation.validation.VacationValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;
    private final VacationMapper vacationMapper;
    private final VacationValidator vacationValidator;

    @Override
    public VacationResponse create(VacationRequest request) {

        vacationValidator.validateDates(request.startDate(), request.endDate());

        User currentUser = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        // Adicionar penas no approve
        // vacationValidator.validateOverlapping(request.startDate(), request.endDate());

        Vacation vacation = vacationRepository.save(
                new Vacation(
                        employee,
                        request.startDate(),
                        request.endDate(),
                        VacationStatus.PENDING
                )
        );

        return vacationMapper.toResponse(vacation);
    }

    @Override
    public void update(UUID id, VacationRequest request) {

        vacationValidator.validateDates(request.startDate(), request.endDate());

        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vacation.not_found", id));

        User currentUser = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        Employee employee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        vacationValidator.validateUpdate(vacation, employee);

        vacation.setStartDate(request.startDate());
        vacation.setEndDate(request.endDate());
    }
}
