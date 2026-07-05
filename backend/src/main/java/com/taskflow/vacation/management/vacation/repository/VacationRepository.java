package com.taskflow.vacation.management.vacation.repository;

import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VacationRepository extends JpaRepository<Vacation, UUID>, JpaSpecificationExecutor<Vacation> {

    boolean existsByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            VacationStatus status,
            LocalDate endDate,
            LocalDate startDate
    );

    @Query("SELECT COUNT(DISTINCT v.employee.id) FROM Vacation v WHERE v.employee.id IN :employeeIds")
    long countDistinctCollaboratorsByEmployeeIds(@Param("employeeIds") List<UUID> employeeIds);

    @Query("SELECT COUNT(DISTINCT v.employee.id) FROM Vacation v")
    long countDistinctCollaborators();

    long countByStatusAndEmployeeIdIn(VacationStatus status, List<UUID> employeeIds);

    long countByStatus(VacationStatus status);
}