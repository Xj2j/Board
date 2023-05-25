package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_member_invites")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectMemberInvite extends ProjectBaseModel {

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "accepted")
    private boolean accepted;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "message", columnDefinition = "text", nullable = true)
    private String message;

    @Column(name = "responded_at", nullable = true)
    private LocalDateTime respondedAt;

    @Column(name = "role")
    private Integer role;

    public String toString() {
        return String.format("%s %s %s", this.getProject().getName(), this.getEmail(), this.isAccepted());
    }
}
