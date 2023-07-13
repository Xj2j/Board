package ru.xj2j.board.userteamservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteRequest {

    private Long id;
    private String email;
    private byte[] token;
    private String role;
    private Long workspaceId;
    private String currentSite;
    private String invitor;
    private boolean slackEnabled;
}
