package ru.xj2j.board.userteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDTO;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDTO;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.exception.InviteWorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.service.WorkspaceMemberService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/members")
public class WorkspaceMemberController {

    private WorkspaceMemberService workspaceMemberService;

    @Autowired
    public WorkspaceMemberController(WorkspaceMemberService workspaceMemberService) {
        this.workspaceMemberService = workspaceMemberService;
    }

    @GetMapping
    public List<WorkspaceMemberDTO> getAllMembers(@PathVariable Long workspaceId) {
        return workspaceMemberService.getAllMembers(workspaceId);
    }

    @PostMapping
    public ResponseEntity<WorkspaceMemberDTO> addMember(@PathVariable Long workspaceId,
                                                        @RequestBody WorkspaceMemberCreateDTO memberDto) {
        WorkspaceMemberDTO addedMember = workspaceMemberService.addMember(workspaceId, memberDto);
        if (addedMember != null) {
            return new ResponseEntity<>(addedMember, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<WorkspaceMemberDTO> updateMember(@PathVariable Long workspaceId,
                                                           @PathVariable Long memberId,
                                                           @RequestBody WorkspaceMemberDTO memberDto,
                                                           @AuthenticationPrincipal User user) {
        WorkspaceMemberDTO updatedMember = workspaceMemberService.updateMember(workspaceId, memberId, memberDto, user);
        if (updatedMember != null) {
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{memberId}")
    public WorkspaceMemberDTO updateWorkspaceMemberRole(@PathVariable Long workspaceId, @PathVariable Long memberId, @RequestBody WorkspaceMemberDTO workspaceMemberDTO) throws InviteWorkspaceNotFoundException {
        return workspaceMemberService.updateWorkspaceMemberRole(workspaceId, memberId, workspaceMemberDTO);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long workspaceId,
                                             @PathVariable Long memberId,
                                             @AuthenticationPrincipal User user) {
        boolean deleted = workspaceMemberService.deleteMember(workspaceId, memberId, user);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
