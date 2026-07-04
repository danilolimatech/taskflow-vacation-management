package com.taskflow.vacation.management.employee.mapper;

import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.dto.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerName", source = "manager.fullName")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "role", source = "user.role")
    EmployeeResponse toResponse(Employee employee);
}
