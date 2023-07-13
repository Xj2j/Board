package ru.xj2j.board.userteamservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinWorkspaceRequest {

    private Long id;
    private boolean isAccepted;
    private LocalDateTime respondedAt;
}
