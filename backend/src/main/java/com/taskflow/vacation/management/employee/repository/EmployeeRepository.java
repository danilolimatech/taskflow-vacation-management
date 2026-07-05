package com.taskflow.vacation.management.employee.repository;

import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
    Optional<Employee> findByUser(User user);
    boolean existsByManagerId(UUID managerId);
    List<Employee> findAllByManagerId(UUID managerId);
}
