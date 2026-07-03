package com.taskflow.vacation.management.employee.repository;

import com.taskflow.vacation.management.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    boolean existsByEmailAndIdNot(String email, UUID id);}
