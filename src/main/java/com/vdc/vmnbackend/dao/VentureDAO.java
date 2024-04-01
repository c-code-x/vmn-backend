package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.VentureStage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

/**
 * Represents a venture entity in the system.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "venture")
public class VentureDAO {

    /**
     * The unique identifier for the venture.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID vid;

    /**
     * The coach associated with the venture.
     */
    @ManyToOne
    @JoinColumn(name = "coachId", referencedColumnName = "uid")
    private UserDAO coachId;

    /**
     * The name of the venture.
     */
    @Column(name = "name")
    private String name;

    /**
     * The campus where the venture operates.
     */
    @Column(name = "Campus")
    private Campus campus;

    /**
     * The tag associated with the venture.
     */
    @Column(name = "tag", updatable = false)
    private String tag;

    /**
     * The stage of the venture.
     */
    @Column(name = "stage")
    private VentureStage stage;

    /**
     * The service area of the venture.
     */
    @Column(name = "serviceArea")
    private String serviceArea;

    /**
     * The bio or description of the venture.
     */
    @Column(name = "bio")
    private String bio = "This is a venture bio";

    /**
     * The sector or industry of the venture.
     */
    @Column(name = "sector", nullable = false)
    private String sector;
}
