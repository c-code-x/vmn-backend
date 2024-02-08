package com.vdc.vmnbackend.dao;

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
@Table(name = "mentor")
public class MentorDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mentorId", referencedColumnName = "uid", updatable = false,nullable = false)
    @MapsId
    private UserDAO mentorId;

    @ManyToOne
    @JoinColumn(name = "adminId", referencedColumnName = "uid", nullable = false)
    private UserDAO adminId;

    @Column(name = "bio")
    @Builder.Default
    private String bio = "I am a mentor, This is My Bio!";

    @Column(name = "expertise")
    private String expertise;

    @Column(name = "experience")
    private String experience;

}
