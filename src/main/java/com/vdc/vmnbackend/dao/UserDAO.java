package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Represents a user entity in the system.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
@Builder
public class UserDAO {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "uid", unique = true, updatable = false, nullable = false)
    private UUID uid;

    /**
     * The name of the user.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The email ID of the user.
     */
    @Column(name = "emailId", nullable = false, unique = true)
    private String emailId;

    /**
     * The role of the user.
     */
    @Column(name = "role", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Roles role = Roles.USER;

    /**
     * The password of the user.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The contact number of the user.
     */
    @Column
    private String contact;

    /**
     * The LinkedIn profile of the user.
     */
    @Column
    private String linkedIn;

    /**
     * The designation of the user.
     */
    @Column
    private String designation;
}
