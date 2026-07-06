package com.taskflow.vacation.management.vacation.controller;

import com.taskflow.vacation.management.vacation.dto.VacationRequest;
import com.taskflow.vacation.management.vacation.dto.VacationResponse;
import com.taskflow.vacation.management.vacation.dto.VacationSummaryResponse;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import com.taskflow.vacation.management.vacation.service.VacationService;
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
@RequestMapping("/v1/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('COLLABORATOR')")
    public VacationResponse create(@Valid @RequestBody VacationRequest request) {
        return vacationService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('COLLABORATOR')")
    public void update(
            @PathVariable UUID id,
            @Valid @RequestBody VacationRequest request
    ) {
        vacationService.update(id, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COLLABORATOR')")
    public Page<VacationResponse> findAll(
            @RequestParam(required = false) String employeeName,
            @RequestParam(required = false) VacationStatus status,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable
    ) {
        return vacationService.findAll(employeeName, status, pageable);
    }

    @GetMapping("/summary")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COLLABORATOR')")
    public VacationSummaryResponse getSummary() {
        return vacationService.getSummary();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'COLLABORATOR')")
    public VacationResponse findById(@PathVariable UUID id) {
        return vacationService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    public void delete(@PathVariable UUID id) {
        vacationService.delete(id);
    }

    @PatchMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void approve(@PathVariable UUID id) {
        vacationService.approve(id);
    }

    @PatchMapping("/{id}/reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void reject(@PathVariable UUID id) {
        vacationService.reject(id);
    }
}
