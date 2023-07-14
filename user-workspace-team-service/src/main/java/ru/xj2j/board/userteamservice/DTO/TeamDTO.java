package ru.xj2j.board.userteamservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xj2j.board.userteamservice.entity.Team;
import ru.xj2j.board.userteamservice.entity.Workspace;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private String description;
    private Set<UserDTO> members;
    private WorkspaceDTO workspace;
    private LocalDateTime createdAt;
}
