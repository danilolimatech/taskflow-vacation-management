package com.taskflow.vacation.management.vacation.entity;

import com.taskflow.vacation.management.common.entity.AuditableEntity;
import com.taskflow.vacation.management.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vacations")
@SQLRestriction("deleted_at IS NULL")
public class Vacation extends AuditableEntity {

    public Vacation(
            Employee employee,
            LocalDate startDate,
            LocalDate endDate,
            VacationStatus status
    ) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VacationStatus status;
}