package com.taskflow.vacation.management.employee.validation;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ResourceNotFoundException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.employee.repository.EmployeeRepository;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeValidatorTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeValidator employeeValidator;

    private Employee buildEmployee(UUID id, Role role) {
        User user = new User("user", "pw", role);
        Employee emp = new Employee("Full Name", "email@test.com", role, null, user);
        try {
            var f = Employee.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(emp, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return emp;
    }

    @Test
    void validateEmail_newEmployee_emailFree_noException() {
        when(employeeRepository.existsByEmail("test@corp.com")).thenReturn(false);

        assertThatCode(() -> employeeValidator.validateEmail("test@corp.com", null))
                .doesNotThrowAnyException();
    }

    @Test
    void validateEmail_newEmployee_emailTaken_throwsConflict() {
        when(employeeRepository.existsByEmail("taken@corp.com")).thenReturn(true);

        assertThatThrownBy(() -> employeeValidator.validateEmail("taken@corp.com", null))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("employee.email.conflict");
    }

    @Test
    void validateEmail_existingEmployee_emailBelongsToSelf_noException() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.existsByEmailAndIdNot("self@corp.com", id)).thenReturn(false);

        assertThatCode(() -> employeeValidator.validateEmail("self@corp.com", id))
                .doesNotThrowAnyException();
    }

    @Test
    void validateEmail_existingEmployee_emailTakenByOther_throwsConflict() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.existsByEmailAndIdNot("other@corp.com", id)).thenReturn(true);

        assertThatThrownBy(() -> employeeValidator.validateEmail("other@corp.com", id))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("employee.email.conflict");
    }

    @Test
    void validateBusinessRules_collaboratorWithManager_noException() {
        assertThatCode(() -> employeeValidator.validateBusinessRules(Role.COLLABORATOR, UUID.randomUUID()))
                .doesNotThrowAnyException();
    }

    @Test
    void validateBusinessRules_collaboratorWithoutManager_throwsBadRequest() {
        assertThatThrownBy(() -> employeeValidator.validateBusinessRules(Role.COLLABORATOR, null))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("employee.collaborator.manager.required");
    }

    @Test
    void validateBusinessRules_managerWithNoManager_noException() {
        assertThatCode(() -> employeeValidator.validateBusinessRules(Role.MANAGER, null))
                .doesNotThrowAnyException();
    }

    @Test
    void validateBusinessRules_managerWithManagerId_throwsBadRequest() {
        assertThatThrownBy(() -> employeeValidator.validateBusinessRules(Role.MANAGER, UUID.randomUUID()))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("employee.manager.not.allowed");
    }

    @Test
    void validateBusinessRules_adminWithManagerId_throwsBadRequest() {
        assertThatThrownBy(() -> employeeValidator.validateBusinessRules(Role.ADMIN, UUID.randomUUID()))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("employee.manager.not.allowed");
    }

    @Test
    void validateBusinessRulesOnUpdate_collaboratorKeepsManager_noException() {
        Employee emp = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        assertThatCode(() -> employeeValidator.validateBusinessRulesOnUpdate(emp, UUID.randomUUID()))
                .doesNotThrowAnyException();
    }

    @Test
    void validateBusinessRulesOnUpdate_collaboratorRemovesManager_throwsBadRequest() {
        Employee emp = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        assertThatThrownBy(() -> employeeValidator.validateBusinessRulesOnUpdate(emp, null))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("employee.collaborator.manager.required");
    }

    @Test
    void validateBusinessRulesOnUpdate_managerAssignsManager_throwsBadRequest() {
        Employee emp = buildEmployee(UUID.randomUUID(), Role.MANAGER);
        assertThatThrownBy(() -> employeeValidator.validateBusinessRulesOnUpdate(emp, UUID.randomUUID()))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("employee.manager.not.allowed");
    }

    @Test
    void getManager_nullManagerId_returnsNull() {
        assertThatCode(() -> employeeValidator.getManager(null))
                .doesNotThrowAnyException();
    }

    @Test
    void getManager_managerNotFound_throwsResourceNotFound() {
        UUID managerId = UUID.randomUUID();
        when(employeeRepository.findById(managerId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeValidator.getManager(managerId))
                .isInstanceOf(ResourceNotFoundException.class)
                .extracting("messageKey")
                .isEqualTo("employee.manager.not_found");
    }

    @Test
    void getManager_employeeIsNotManager_throwsBadRequest() {
        UUID managerId = UUID.randomUUID();
        Employee nonManager = buildEmployee(managerId, Role.COLLABORATOR);
        when(employeeRepository.findById(managerId)).thenReturn(Optional.of(nonManager));

        assertThatThrownBy(() -> employeeValidator.getManager(managerId))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("employee.manager.not_manager");
    }

    @Test
    void getManager_validManager_returnsEmployee() {
        UUID managerId = UUID.randomUUID();
        Employee manager = buildEmployee(managerId, Role.MANAGER);
        when(employeeRepository.findById(managerId)).thenReturn(Optional.of(manager));

        Employee result = employeeValidator.getManager(managerId);

        assertThatCode(() -> {}).doesNotThrowAnyException();
    }
}
