package ru.xj2j.board.userteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xj2j.board.userteamservice.DTO.CreateTeamRequest;
import ru.xj2j.board.userteamservice.DTO.TeamMemberDTO;
import ru.xj2j.board.userteamservice.entity.TeamMember;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.service.TeamMemberService;

import java.util.Set;

@RestController
@RequestMapping("/workspaces/{workspaceId}/members")
public class TeamMemberController {

    private TeamMemberService teamMemberService;

    @Autowired
    public TeamMemberController(TeamMemberService teamMemberService) {
        this.teamMemberService = teamMemberService;
    }

    @GetMapping
    public Set<TeamMember> getTeamMembers(@PathVariable Long workspaceId) {
        return teamMemberService.getTeamMembersByWorkspaceId(workspaceId);
    }

    @PostMapping("")
    public ResponseEntity<?> createTeamMember(@PathVariable("workspaceId") Long workspaceId, @RequestBody CreateTeamRequest request) throws WorkspaceNotFoundException {
        TeamMemberDTO createdTeamMember = teamMemberService.createTeamMember(workspaceId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTeamMember);
    }
}



