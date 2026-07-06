package com.taskflow.vacation.management.vacation.service;

import com.taskflow.vacation.management.vacation.dto.VacationRequest;
import com.taskflow.vacation.management.vacation.dto.VacationResponse;
import com.taskflow.vacation.management.vacation.dto.VacationSummaryResponse;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VacationService {

    VacationResponse create(VacationRequest request);
    void update(UUID id, VacationRequest request);
    VacationResponse findById(UUID id);
    void delete(UUID id);
    void approve(UUID id);
    void reject(UUID id);
    Page<VacationResponse> findAll(String employeeName, VacationStatus status, Pageable pageable);
    VacationSummaryResponse getSummary();
}
