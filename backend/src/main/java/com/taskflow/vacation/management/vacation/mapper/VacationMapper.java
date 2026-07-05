package com.taskflow.vacation.management.vacation.mapper;

import com.taskflow.vacation.management.vacation.dto.VacationResponse;
import com.taskflow.vacation.management.vacation.entity.Vacation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VacationMapper {

    @Mapping(target = "employeeId", source = "employee.id")
    VacationResponse toResponse(Vacation vacation);
}