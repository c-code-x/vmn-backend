package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.Campus;
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
@Table(name = "mentee")
public class MenteeDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menteeId", referencedColumnName = "uid", updatable = false,nullable = false)
    @MapsId
    private UserDAO menteeId;


    @Column(name = "campus", nullable = false)
    private Campus campus;

    @ManyToOne
    @JoinColumn(name = "ventureId", referencedColumnName = "vid")
    private VentureDAO ventureId;

}
