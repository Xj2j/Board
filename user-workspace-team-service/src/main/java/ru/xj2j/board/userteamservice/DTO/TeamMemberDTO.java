package ru.xj2j.board.userteamservice.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import ru.xj2j.board.userteamservice.entity.Team;
import ru.xj2j.board.userteamservice.entity.TeamMember;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberDTO {

    private Long id;
    private WorkspaceDTO workspace;
    private TeamDTO team;
    private UserDTO member;
}
