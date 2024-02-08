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

    @Column(name = "name")
    private String name;

    @Column(name = "Campus")
    private Campus campus;

    @Column(name = "tag", updatable = false)
    private String tag;

    @Column(name = "stage")
    private VentureStage stage;

    @Column(name = "serviceArea")
    private String serviceArea;

    @Column(name = "bio")
    private String bio = "This is a venture bio";

    @Column(name = "sector", nullable = false)
    private String sector;


}
