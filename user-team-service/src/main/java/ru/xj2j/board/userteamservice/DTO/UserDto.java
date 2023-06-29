package ru.xj2j.board.userteamservice.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("is_onboarded")
    private boolean isOnboarded;

    @JsonProperty("workspace")
    private WorkspaceDto workspace;

    /*@JsonProperty("issues")
    private IssuesDto issues = new IssuesDto();*/
}