package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Campus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents an admin entity in the system.
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "admin")
public class AdminDAO {
    /**
     * The unique identifier for the admin entity.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    /**
     * The user associated with this admin.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adminId", referencedColumnName = "uid", nullable = false, updatable = false)
    @MapsId
    private UserDAO adminId;

    /**
     * The user who created this admin entity.
     */
    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "uid", nullable = false)
    private UserDAO createdBy;

    /**
     * The campus where the admin is located.
     */
    @Column(name = "campus")
    private Campus campus;

    /**
     * The biography of the admin.
     */
    @Column(name = "bio")
    @Builder.Default
    private String bio = "I am an admin, This is My Bio!";
}
