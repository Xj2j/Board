package ru.xj2j.board.userteamservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceMemberDto {

    private Long id;
    private Workspace workspace;
    private User member;
    private Integer role;
    private String companyRole;
    private String viewProps;
    private LocalDateTime createdAt;
}
