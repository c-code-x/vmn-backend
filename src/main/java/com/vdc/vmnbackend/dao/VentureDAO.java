package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Campus;
import com.vdc.vmnbackend.enumerators.VentureStage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "venture")
public class VentureDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID vid;

    @ManyToOne
    @JoinColumn(name = "coachId", referencedColumnName = "uid")
    private UserDAO coachId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "Campus", nullable = false)
    private Campus campus;

    @Column(name = "tag", nullable = false, updatable = false)
    private String tag;

    @Column(name = "stage", nullable = false)
    private VentureStage stage;

    @Column(name = "serviceArea", nullable = false)
    private String serviceArea;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "sector", nullable = false)
    private String sector;


}
