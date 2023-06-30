package ru.xj2j.board.userteamservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.xj2j.board.userteamservice.entity.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceDTO {

    private Long id;
    //private String slug;
    private String name;
    private String logo;
    //private String description;
    private User owner;
    private Integer companySize;
    private LocalDateTime createdAt;
}