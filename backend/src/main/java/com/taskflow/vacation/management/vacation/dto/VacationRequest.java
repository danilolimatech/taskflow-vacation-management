package com.taskflow.vacation.management.vacation.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VacationRequest(

        @NotNull
        @FutureOrPresent
        LocalDate startDate,

        @NotNull
        @FutureOrPresent
        LocalDate endDate
) {
}
