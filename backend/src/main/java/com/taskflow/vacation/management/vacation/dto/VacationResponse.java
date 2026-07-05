package com.taskflow.vacation.management.vacation.dto;

import com.taskflow.vacation.management.vacation.entity.VacationStatus;

import java.time.LocalDate;
import java.util.UUID;

public record VacationResponse(

        UUID id,

        UUID employeeId,

        LocalDate startDate,

        LocalDate endDate,

        VacationStatus status
) {
}