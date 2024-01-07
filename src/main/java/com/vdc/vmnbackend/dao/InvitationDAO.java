package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.InvitationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "invitation")
public class InvitationDAO {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID invId;

    @ManyToOne
    @JoinColumn(name = "senderId" ,nullable = false, updatable = false)
    private UserDAO sender;

   @Column(updatable = false,nullable = false)
   private String receiverMailId;

    @Column(updatable = false,nullable = false)
    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status = InvitationStatus.PENDING;

    @Builder.Default
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(updatable = false)
    private LocalDateTime updatedAt;

}
