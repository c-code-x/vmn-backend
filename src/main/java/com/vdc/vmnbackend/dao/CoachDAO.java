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
@Table(name = "coach")
public class CoachDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coachId", referencedColumnName = "uid", nullable = false, updatable = false)
    @MapsId
    private UserDAO coachId;


    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "uid", nullable = false)
    private UserDAO adminId;

    @Column(name = "campus")
    private Campus campus;

    @Column(name = "bio")
    private String bio;


}
