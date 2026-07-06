package com.taskflow.vacation.management.vacation.service;

import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ForbiddenException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.common.security.SecurityUtils;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;
import com.taskflow.vacation.management.vacation.dto.VacationRequest;
import com.taskflow.vacation.management.vacation.dto.VacationResponse;
import com.taskflow.vacation.management.vacation.dto.VacationSummaryResponse;
import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import com.taskflow.vacation.management.vacation.mapper.VacationMapper;
import com.taskflow.vacation.management.vacation.repository.VacationRepository;
import com.taskflow.vacation.management.vacation.repository.VacationSpec;
import com.taskflow.vacation.management.vacation.validation.VacationValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class VacationServiceImpl implements VacationService {

    private final VacationRepository vacationRepository;
    private final EmployeeRepository employeeRepository;
    private final VacationMapper vacationMapper;
    private final VacationValidator vacationValidator;
    private final SecurityUtils securityUtils;

    @Override
    public VacationResponse create(VacationRequest request) {
        log.info("Creating vacation. period={} to {}", request.startDate(), request.endDate());

        vacationValidator.validateDates(request.startDate(), request.endDate());

        User currentUser = securityUtils.getCurrentUser();

        Employee employee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        Vacation vacation = vacationRepository.save(
                new Vacation(employee, request.startDate(), request.endDate(), VacationStatus.PENDING)
        );

        log.info("Vacation created successfully. id={}, employeeId={}", vacation.getId(), employee.getId());

        return vacationMapper.toResponse(vacation);
    }

    @Override
    public void update(UUID id, VacationRequest request) {
        log.info("Updating vacation. id={}, new period={} to {}", id, request.startDate(), request.endDate());

        vacationValidator.validateDates(request.startDate(), request.endDate());

        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vacation.not_found", id));

        User currentUser = securityUtils.getCurrentUser();

        Employee employee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        vacationValidator.validateUpdate(vacation, employee);

        vacation.setStartDate(request.startDate());
        vacation.setEndDate(request.endDate());

        log.info("Vacation updated successfully. id={}", id);
    }

    @Override
    public VacationResponse findById(UUID id) {
        log.debug("Fetching vacation. id={}", id);

        User currentUser = securityUtils.getCurrentUser();

        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vacation.not_found", id));

        if (currentUser.getRole() == Role.ADMIN) {
            log.debug("Vacation fetched successfully. id={}", id);
            return vacationMapper.toResponse(vacation);
        }

        Employee employee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        if (currentUser.getRole() == Role.MANAGER) {
            if (!vacation.getEmployee().getManager().getId().equals(employee.getId())) {
                throw new ForbiddenException("vacation.access_denied");
            }
            log.debug("Vacation fetched successfully. id={}", id);
            return vacationMapper.toResponse(vacation);
        }

        if (!vacation.getEmployee().getId().equals(employee.getId())) {
            throw new ForbiddenException("vacation.access_denied");
        }

        log.debug("Vacation fetched successfully. id={}", id);
        return vacationMapper.toResponse(vacation);
    }

    @Override
    public void delete(UUID id) {
        log.info("Deleting vacation. id={}", id);

        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vacation.not_found", id));

        if (vacation.getStatus() != VacationStatus.PENDING) {
            throw new ConflictException("vacation.not_pending");
        }

        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            Employee employee = employeeRepository.findByUser(currentUser)
                    .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));
            vacationValidator.validateDelete(vacation, employee);
        }

        vacation.delete();

        log.info("Vacation deleted successfully. id={}, deletedBy={}", id, currentUser.getUsername());
    }

    @Override
    public void approve(UUID id) {
        log.info("Approving vacation. id={}", id);

        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vacation.not_found", id));

        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            Employee reviewer = employeeRepository.findByUser(currentUser)
                    .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));
            vacationValidator.validateApproveOrReject(vacation, reviewer);
        } else {
            vacationValidator.validateApproveOrReject(vacation, null);
        }

        vacationValidator.validateOverlapping(vacation.getStartDate(), vacation.getEndDate());

        vacation.setStatus(VacationStatus.APPROVED);

        log.info("Vacation approved successfully. id={}, approvedBy={}", id, currentUser.getUsername());
    }

    @Override
    public void reject(UUID id) {
        log.info("Rejecting vacation. id={}", id);

        Vacation vacation = vacationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("vacation.not_found", id));

        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getRole() != Role.ADMIN) {
            Employee reviewer = employeeRepository.findByUser(currentUser)
                    .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));
            vacationValidator.validateApproveOrReject(vacation, reviewer);
        } else {
            vacationValidator.validateApproveOrReject(vacation, null);
        }

        vacation.setStatus(VacationStatus.REJECTED);

        log.info("Vacation rejected successfully. id={}, rejectedBy={}", id, currentUser.getUsername());
    }

    @Override
    public Page<VacationResponse> findAll(String employeeName, VacationStatus status, Pageable pageable) {
        User currentUser = securityUtils.getCurrentUser();

        log.debug("Listing vacations. user={}, role={}, filter=employeeName:{}, status:{}, page={}",
                currentUser.getUsername(), currentUser.getRole(), employeeName, status, pageable.getPageNumber());

        Specification<Vacation> spec = Specification.allOf(
                VacationSpec.hasEmployeeNameLike(employeeName),
                VacationSpec.hasStatus(status)
        );

        if (currentUser.getRole() == Role.ADMIN) {
            return vacationRepository.findAll(spec, pageable).map(vacationMapper::toResponse);
        }

        Employee currentEmployee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        if (currentUser.getRole() == Role.MANAGER) {
            List<UUID> subordinateIds = employeeRepository.findAllByManagerId(currentEmployee.getId())
                    .stream().map(Employee::getId).toList();
            spec = spec.and(VacationSpec.hasEmployeeIdIn(subordinateIds));
        } else {
            spec = spec.and(VacationSpec.hasEmployeeId(currentEmployee.getId()));
        }

        return vacationRepository.findAll(spec, pageable).map(vacationMapper::toResponse);
    }

    @Override
    public VacationSummaryResponse getSummary() {
        User currentUser = securityUtils.getCurrentUser();

        log.debug("Fetching vacation summary. user={}, role={}", currentUser.getUsername(), currentUser.getRole());

        if (currentUser.getRole() == Role.ADMIN) {
            return new VacationSummaryResponse(
                    vacationRepository.countDistinctCollaborators(),
                    vacationRepository.countByStatus(VacationStatus.PENDING),
                    vacationRepository.countByStatus(VacationStatus.APPROVED),
                    vacationRepository.countByStatus(VacationStatus.REJECTED)
            );
        }

        Employee currentEmployee = employeeRepository.findByUser(currentUser)
                .orElseThrow(() -> new ResourceNotFoundException("employee.not_found", currentUser.getId()));

        List<UUID> employeeIds;

        if (currentUser.getRole() == Role.MANAGER) {
            employeeIds = employeeRepository.findAllByManagerId(currentEmployee.getId())
                    .stream().map(Employee::getId).toList();
        } else {
            employeeIds = List.of(currentEmployee.getId());
        }

        return new VacationSummaryResponse(
                vacationRepository.countDistinctCollaboratorsByEmployeeIds(employeeIds),
                vacationRepository.countByStatusAndEmployeeIdIn(VacationStatus.PENDING, employeeIds),
                vacationRepository.countByStatusAndEmployeeIdIn(VacationStatus.APPROVED, employeeIds),
                vacationRepository.countByStatusAndEmployeeIdIn(VacationStatus.REJECTED, employeeIds)
        );
    }
}
