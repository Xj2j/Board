package ru.xj2j.board.userteamservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberCreateDTO {
    private Long id;
    private WorkspaceDTO workspace;
    private UserDTO member;
    private String role;
}
