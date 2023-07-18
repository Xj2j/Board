package ru.xj2j.board.userteamservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueCompletedGraphDTO {
    private int week;
    private int completedCount;
}
