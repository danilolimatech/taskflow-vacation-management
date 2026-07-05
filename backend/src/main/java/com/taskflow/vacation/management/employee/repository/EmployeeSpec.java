package com.taskflow.vacation.management.employee.repository;

import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.user.entity.Role;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;
import java.util.UUID;

public class EmployeeSpec {

    private EmployeeSpec() {}

    public static Specification<Employee> hasManagerId(UUID managerId) {
        return (root, query, cb) -> managerId == null ? null
                : cb.equal(root.get("manager").get("id"), managerId);
    }

    public static Specification<Employee> hasFullNameLike(String fullName) {
        return (root, query, cb) -> fullName == null ? null
                : cb.like(cb.lower(root.get("fullName")), "%" + fullName.toLowerCase(Locale.ROOT) + "%");
    }

    public static Specification<Employee> hasEmailLike(String email) {
        return (root, query, cb) -> email == null ? null
                : cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase(Locale.ROOT) + "%");
    }

    public static Specification<Employee> hasRole(Role role) {
        return (root, query, cb) ->
                role == null ? null
                        : cb.equal(root.get("user").get("role"), role);
    }
}
