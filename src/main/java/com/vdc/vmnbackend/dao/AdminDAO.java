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
@Table(name = "admin")
public class AdminDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID dataId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "adminId", referencedColumnName = "uid", nullable = false, updatable = false)
    @MapsId
    private UserDAO adminId;


    @ManyToOne
    @JoinColumn(name = "createdBy", referencedColumnName = "uid",nullable = false)
    private UserDAO createdBy;

    @Column(name = "campus")
    private Campus campus;

    @Column(name = "bio")
    @Builder.Default
    private String bio = "I am an admin, This is My Bio!";
}
