package com.Document.DocAudit.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // user id
    private String provider; // user login provider name(eg. google)
    private String providerId;// user login provider id
    private String email; // user Email
    private String name; // user name
    private String pictureUrl; // user profile url
}
