package com.taskflow.vacation.management.user.entity;

import com.taskflow.vacation.management.common.entity.AuditableEntity;
import com.taskflow.vacation.management.employee.domain.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@SQLRestriction("deleted_at IS NULL")
public class User extends AuditableEntity {

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void deactivate() {
        delete();
    }
}