package com.taskflow.vacation.management.auth.controller;

import com.taskflow.vacation.management.auth.dto.LoginRequest;
import com.taskflow.vacation.management.auth.dto.LoginResponse;
import com.taskflow.vacation.management.common.exception.domain.BadRequestException;
import com.taskflow.vacation.management.common.security.JwtService;
import com.taskflow.vacation.management.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            User user = (User) authentication.getPrincipal();
            String token = jwtService.generateToken(user, Map.of(
                    "username", user.getUsername(),
                    "role", user.getRole()
            ));
            return new LoginResponse(token, user.getId(), user.getUsername(), user.getRole());
        } catch (BadCredentialsException e) {
            throw new BadRequestException("auth.invalid.credentials", e);
        }
    }
}
