package com.taskflow.vacation.management.vacation.dto;

public record VacationSummaryResponse(

        long totalCollaborators,
        long totalPending,
        long totalApproved,
        long totalRejected
) {
}
