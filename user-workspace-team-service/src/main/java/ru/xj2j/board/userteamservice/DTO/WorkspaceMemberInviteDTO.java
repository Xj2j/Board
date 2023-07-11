package ru.xj2j.board.userteamservice.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import ru.xj2j.board.userteamservice.entity.Workspace;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberInviteDTO {

    private Long id;
    private String email;
    private WorkspaceDTO workspace;
    private String token;
    private String role;
    private Boolean accepted;
    private String message;
    private LocalDateTime respondedAt;
    private LocalDateTime createdAt;
}
