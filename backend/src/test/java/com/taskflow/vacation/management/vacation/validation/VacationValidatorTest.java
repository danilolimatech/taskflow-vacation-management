package com.taskflow.vacation.management.vacation.validation;

import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.exception.domain.ConflictException;
import com.taskflow.vacation.management.common.exception.domain.ForbiddenException;
import com.taskflow.vacation.management.employee.domain.Employee;
import com.taskflow.vacation.management.user.entity.Role;
import com.taskflow.vacation.management.user.entity.User;
import com.taskflow.vacation.management.vacation.entity.Vacation;
import com.taskflow.vacation.management.vacation.entity.VacationStatus;
import com.taskflow.vacation.management.vacation.repository.VacationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VacationValidatorTest {

    @Mock
    VacationRepository vacationRepository;

    @InjectMocks
    VacationValidator vacationValidator;

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

    private Employee buildEmployeeWithManager(UUID empId, UUID managerId) {
        Employee manager = buildEmployee(managerId, Role.MANAGER);
        User user = new User("user", "pw", Role.COLLABORATOR);
        Employee emp = new Employee("Collab", "collab@test.com", Role.COLLABORATOR, manager, user);
        try {
            var f = Employee.class.getDeclaredField("id");
            f.setAccessible(true);
            f.set(emp, empId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return emp;
    }

    private Vacation buildVacation(Employee employee, VacationStatus status) {
        return new Vacation(employee, LocalDate.now().plusDays(1), LocalDate.now().plusDays(10), status);
    }

    @Test
    void validateDates_startBeforeEnd_noException() {
        assertThatCode(() -> vacationValidator.validateDates(
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(10)))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDates_startEqualsEnd_noException() {
        LocalDate date = LocalDate.now().plusDays(1);
        assertThatCode(() -> vacationValidator.validateDates(date, date))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDates_startAfterEnd_throwsBadRequest() {
        assertThatThrownBy(() -> vacationValidator.validateDates(
                LocalDate.now().plusDays(10), LocalDate.now().plusDays(1)))
                .isInstanceOf(BadRequestException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.invalid_period");
    }

    @Test
    void validateOverlapping_noConflict_noException() {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(10);
        when(vacationRepository.existsByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                VacationStatus.APPROVED, end, start)).thenReturn(false);

        assertThatCode(() -> vacationValidator.validateOverlapping(start, end))
                .doesNotThrowAnyException();
    }

    @Test
    void validateOverlapping_conflictExists_throwsConflict() {
        LocalDate start = LocalDate.now().plusDays(1);
        LocalDate end = LocalDate.now().plusDays(10);
        when(vacationRepository.existsByStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                VacationStatus.APPROVED, end, start)).thenReturn(true);

        assertThatThrownBy(() -> vacationValidator.validateOverlapping(start, end))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.overlapping");
    }

    @Test
    void validateUpdate_ownPendingVacation_noException() {
        UUID empId = UUID.randomUUID();
        Employee employee = buildEmployee(empId, Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.PENDING);

        assertThatCode(() -> vacationValidator.validateUpdate(vacation, employee))
                .doesNotThrowAnyException();
    }

    @Test
    void validateUpdate_differentEmployee_throwsForbidden() {
        UUID empId = UUID.randomUUID();
        Employee owner = buildEmployee(empId, Role.COLLABORATOR);
        Employee other = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        Vacation vacation = buildVacation(owner, VacationStatus.PENDING);

        assertThatThrownBy(() -> vacationValidator.validateUpdate(vacation, other))
                .isInstanceOf(ForbiddenException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.access_denied");
    }

    @Test
    void validateUpdate_notPendingVacation_throwsConflict() {
        UUID empId = UUID.randomUUID();
        Employee employee = buildEmployee(empId, Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.APPROVED);

        assertThatThrownBy(() -> vacationValidator.validateUpdate(vacation, employee))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.not_pending");
    }

    @Test
    void validateDelete_ownPendingVacation_noException() {
        UUID empId = UUID.randomUUID();
        Employee employee = buildEmployee(empId, Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.PENDING);

        assertThatCode(() -> vacationValidator.validateDelete(vacation, employee))
                .doesNotThrowAnyException();
    }

    @Test
    void validateDelete_differentEmployee_throwsForbidden() {
        UUID empId = UUID.randomUUID();
        Employee owner = buildEmployee(empId, Role.COLLABORATOR);
        Employee other = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        Vacation vacation = buildVacation(owner, VacationStatus.PENDING);

        assertThatThrownBy(() -> vacationValidator.validateDelete(vacation, other))
                .isInstanceOf(ForbiddenException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.access_denied");
    }

    @Test
    void validateDelete_approvedVacation_throwsConflict() {
        UUID empId = UUID.randomUUID();
        Employee employee = buildEmployee(empId, Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.APPROVED);

        assertThatThrownBy(() -> vacationValidator.validateDelete(vacation, employee))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.not_pending");
    }

    @Test
    void validateDelete_rejectedVacation_throwsConflict() {
        UUID empId = UUID.randomUUID();
        Employee employee = buildEmployee(empId, Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.REJECTED);

        assertThatThrownBy(() -> vacationValidator.validateDelete(vacation, employee))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.not_pending");
    }

    @Test
    void validateApproveOrReject_pendingVacationAsAdmin_noException() {
        Employee employee = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.PENDING);

        assertThatCode(() -> vacationValidator.validateApproveOrReject(vacation, null))
                .doesNotThrowAnyException();
    }

    @Test
    void validateApproveOrReject_notPending_throwsConflict() {
        Employee employee = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.APPROVED);

        assertThatThrownBy(() -> vacationValidator.validateApproveOrReject(vacation, null))
                .isInstanceOf(ConflictException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.not_pending");
    }

    @Test
    void validateApproveOrReject_managerIsDirectSupervisor_noException() {
        UUID managerId = UUID.randomUUID();
        Employee manager = buildEmployee(managerId, Role.MANAGER);
        Employee employee = buildEmployeeWithManager(UUID.randomUUID(), managerId);
        Vacation vacation = buildVacation(employee, VacationStatus.PENDING);

        assertThatCode(() -> vacationValidator.validateApproveOrReject(vacation, manager))
                .doesNotThrowAnyException();
    }

    @Test
    void validateApproveOrReject_managerIsNotSupervisor_throwsForbidden() {
        UUID managerId = UUID.randomUUID();
        Employee reviewer = buildEmployee(UUID.randomUUID(), Role.MANAGER);
        Employee employee = buildEmployeeWithManager(UUID.randomUUID(), managerId);
        Vacation vacation = buildVacation(employee, VacationStatus.PENDING);

        assertThatThrownBy(() -> vacationValidator.validateApproveOrReject(vacation, reviewer))
                .isInstanceOf(ForbiddenException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.access_denied");
    }

    @Test
    void validateApproveOrReject_employeeHasNoManager_throwsForbidden() {
        UUID managerId = UUID.randomUUID();
        Employee reviewer = buildEmployee(managerId, Role.MANAGER);
        Employee employee = buildEmployee(UUID.randomUUID(), Role.COLLABORATOR);
        Vacation vacation = buildVacation(employee, VacationStatus.PENDING);

        assertThatThrownBy(() -> vacationValidator.validateApproveOrReject(vacation, reviewer))
                .isInstanceOf(ForbiddenException.class)
                .extracting("messageKey")
                .isEqualTo("vacation.access_denied");
    }
}
