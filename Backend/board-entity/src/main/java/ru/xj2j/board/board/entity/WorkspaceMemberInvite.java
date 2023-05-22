package ru.xj2j.board.board.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "workspace_member_invites")
@EntityListeners(AuditingEntityListener.class)
public class WorkspaceMemberInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
    private Workspace workspace;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "accepted", nullable = false)
    private Boolean accepted;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "message")
    private String message;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Column(name = "role", nullable = false)
    private Integer role;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return workspace.getName() + " " + email + " " + accepted;
    }
}
