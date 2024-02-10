package com.vdc.vmnbackend.dao;

import com.vdc.vmnbackend.enumerators.InvitationStatus;
import com.vdc.vmnbackend.enumerators.Roles;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an invitation entity in the system.
 */
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "invitation")
public class InvitationDAO {

    /**
     * The unique identifier for the invitation.
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    private UUID invId;

    /**
     * The user who sent the invitation.
     */
    @ManyToOne
    @JoinColumn(name = "senderId", nullable = false, updatable = false)
    private UserDAO sender;

    /**
     * The email address of the receiver of the invitation.
     */
    @Column(updatable = false, nullable = false)
    private String receiverMailId;

    /**
     * The name of the receiver of the invitation.
     */
    @Column(updatable = false, nullable = false)
    private String name;

    /**
     * The role to which the receiver is invited.
     */
    @Column(updatable = false, nullable = false)
    private Roles toRole;

    /**
     * The status of the invitation.
     */
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status = InvitationStatus.PENDING;

    /**
     * The timestamp when the invitation was created.
     */
    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * The venture associated with the invitation (if any).
     */
    @ManyToOne
    @JoinColumn(name = "ventureId")
    private VentureDAO venture;
}
