package com.taskflow.vacation.management.vacation.repository;

import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface VacationRepository extends JpaRepository<Vacation, UUID> {

    boolean existsByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            VacationStatus status,
            LocalDate endDate,
            LocalDate startDate
    );
}