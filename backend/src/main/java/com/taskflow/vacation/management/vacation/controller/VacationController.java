package com.taskflow.vacation.management.vacation.controller;

import com.taskflow.vacation.management.vacation.dto.VacationRequest;
import com.taskflow.vacation.management.vacation.dto.VacationResponse;
import com.taskflow.vacation.management.vacation.service.VacationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLABORATOR')")
    public void update(
            @PathVariable UUID id,
            @Valid @RequestBody VacationRequest request
    ) {
        vacationService.update(id, request);
    }
}