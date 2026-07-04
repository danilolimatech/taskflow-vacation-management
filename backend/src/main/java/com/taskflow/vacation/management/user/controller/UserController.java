package com.taskflow.vacation.management.user.controller;

import com.taskflow.vacation.management.common.exception.domain.ForbiddenException;
import com.taskflow.vacation.management.user.service.UserService;
import com.taskflow.vacation.management.user.dto.ChangePasswordRequest;
import com.taskflow.vacation.management.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable UUID id, @Valid @RequestBody ChangePasswordRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentUser.getId().equals(id)) {
            throw new ForbiddenException("access.denied");
        }
        userService.changePassword(id, request);
    }
}
