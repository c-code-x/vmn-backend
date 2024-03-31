package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Campus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents a coach entity in the system.
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "coach")
public class CoachDAO {

    /**
     * The unique identifier for the coach entity.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    /**
     * The user associated with this coach.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coachId", referencedColumnName = "uid", nullable = false, updatable = false)
    @MapsId
    private UserDAO coachId;

    /**
     * The admin user who oversees this coach.
     */
    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "uid", nullable = false)
    private UserDAO adminId;

    /**
     * The campus where the coach is located.
     */
    @Column(name = "campus")
    private Campus campus;

    /**
     * The bio of the coach.
     */
    @Column(name = "bio")
    @Builder.Default
    private String bio = "Coach's biography is not available.";
}
