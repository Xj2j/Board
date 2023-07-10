package ru.xj2j.board.userteamservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workspace_members")
@EntityListeners(AuditingEntityListener.class)
public class WorkspaceMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public WorkspaceMember(Workspace workspace, User member, String role) {
        this.workspace = workspace;
        this.member = member;
        this.role = role;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", referencedColumnName = "id", nullable = false)
    private Workspace workspace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private User member;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "company_role")
    private String companyRole;

    @Column(name = "view_props")
    private String viewProps;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    @Override
    public String toString() {
        return member.getEmail() + " <" + workspace.getName() + ">";
    }
}
