package com.taskflow.vacation.management.user.repository;

import com.taskflow.vacation.management.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, UUID id);
    Optional<User> findByUsername(String username);
}
