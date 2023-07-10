package ru.xj2j.board.userteamservice.DTO;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberDTO {

    private Long id;
    private WorkspaceDTO workspace;
    private UserDTO member;
    private String role;
    private String companyRole;
    private String viewProps;
    private LocalDateTime createdAt;

}
