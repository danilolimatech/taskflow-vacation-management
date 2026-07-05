package com.taskflow.vacation.management.vacation.service;

import com.taskflow.vacation.management.vacation.dto.VacationRequest;
import com.taskflow.vacation.management.vacation.dto.VacationResponse;

import java.util.UUID;

public interface VacationService {

    VacationResponse create(VacationRequest request);
    void update(UUID id, VacationRequest request);

}
