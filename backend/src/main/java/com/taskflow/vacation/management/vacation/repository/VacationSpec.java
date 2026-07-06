package com.taskflow.vacation.management.vacation.repository;

import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class VacationSpec {

    private VacationSpec() {}

    public static Specification<Vacation> hasEmployeeId(UUID employeeId) {
        return (root, query, cb) -> employeeId == null ? null
                : cb.equal(root.get("employee").get("id"), employeeId);
    }

    public static Specification<Vacation> hasEmployeeIdIn(List<UUID> employeeIds) {
        return (root, query, cb) -> (employeeIds == null || employeeIds.isEmpty()) ? null
                : root.get("employee").get("id").in(employeeIds);
    }

    public static Specification<Vacation> hasEmployeeNameLike(String name) {
        return (root, query, cb) -> name == null ? null
                : cb.like(cb.lower(root.get("employee").get("fullName")), "%" + name.toLowerCase(Locale.ROOT) + "%");
    }

    public static Specification<Vacation> hasStatus(VacationStatus status) {
        return (root, query, cb) -> status == null ? null
                : cb.equal(root.get("status"), status);
    }
}
