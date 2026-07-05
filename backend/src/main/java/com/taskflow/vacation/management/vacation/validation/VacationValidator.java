package com.taskflow.vacation.management.vacation.validation;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ForbiddenException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import com.taskflow.vacation.management.vacation.repository.VacationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class VacationValidator {

    private final VacationRepository vacationRepository;

    public void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("vacation.invalid_period");
        }
    }

    public void validateOverlapping(LocalDate startDate, LocalDate endDate) {
        boolean exists = vacationRepository.existsByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                VacationStatus.APPROVED,
                endDate,
                startDate
        );

        if (exists) {
            throw new ConflictException("vacation.overlapping");
        }
    }

    public void validateUpdate(Vacation vacation, Employee employee) {

        if (!vacation.getEmployee().getId().equals(employee.getId())) {
            throw new ForbiddenException("vacation.access_denied");
        }

        if (vacation.getStatus() != VacationStatus.PENDING) {
            throw new ConflictException("vacation.not_pending");
        }
    }
}