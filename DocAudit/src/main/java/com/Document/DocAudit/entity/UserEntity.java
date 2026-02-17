package com.Document.DocAudit.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider","providerId"}
        )})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // user id
    @Column(nullable = false)
    private String provider; // user login provider name(eg. google)
    @Column(nullable = false)
    private String providerId;// user login provider id
    @Column(nullable = false)
    private String email; // user Email
    private String name; // user name
    private String pictureUrl; // user profile url
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime lastLoginAt;

}
