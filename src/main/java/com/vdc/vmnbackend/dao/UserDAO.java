package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
@Builder
public class UserDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "uid", unique = true, updatable = false, nullable = false)
    private UUID uid;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "emailId", nullable = false, unique = true)
    private String emailId;

    @Column(name = "role", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    @Column(name = "password",nullable = false)
    private String password;

    @Column
    private String contact;

    @Column
    private String linkedIn;

    @Column
    private String designation;


}
