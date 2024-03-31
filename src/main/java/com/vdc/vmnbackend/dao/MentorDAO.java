package com.vdc.vmnbackend.dao;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Represents a mentor entity in the system.
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "mentor")
public class MentorDAO {
    /**
     * The unique identifier for the mentor.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    /**
     * The user who is a mentor.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentorId", referencedColumnName = "uid", updatable = false, nullable = false)
    @MapsId
    private UserDAO mentorId;

    /**
     * The admin user who created the mentor.
     */
    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "uid", nullable = false)
    private UserDAO adminId;

    /**
     * The biography of the mentor.
     */
    @Column(name = "bio")
    @Builder.Default
    private String bio = "I am a mentor, This is My Bio!";

    /**
     * The expertise of the mentor.
     */
    @Column(name = "expertise")
    private String expertise;

    /**
     * The experience of the mentor.
     */
    @Column(name = "experience")
    private String experience;
}
