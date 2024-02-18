package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Campus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Represents a mentee entity in the system.
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "mentee")
public class MenteeDAO {

    /**
     * The unique identifier for the mentee.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    /**
     * The user who is a mentee.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menteeId", referencedColumnName = "uid", updatable = false, nullable = false)
    @MapsId
    private UserDAO menteeId;

    /**
     * The campus to which the mentee belongs.
     */
    @Column(name = "campus", nullable = false)
    private Campus campus;

    /**
     * The venture associated with the mentee (if any).
     */
    @ManyToOne
    @JoinColumn(name = "ventureId", referencedColumnName = "vid")
    private VentureDAO ventureId;
}
