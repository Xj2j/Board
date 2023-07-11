package ru.xj2j.board.userteamservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workspace_member_invites")
@EntityListeners(AuditingEntityListener.class)
public class WorkspaceMemberInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public WorkspaceMemberInvite(String email, Workspace workspace, String token, String role) {
        this.email = email;
        this.workspace = workspace;
        this.token = token;
        this.role = role;
    }

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
    private Workspace workspace;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "accepted")
    private Boolean accepted;


    @Column(name = "message")
    private String message;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return workspace.getName() + " " + email + " " + accepted;
    }
}
