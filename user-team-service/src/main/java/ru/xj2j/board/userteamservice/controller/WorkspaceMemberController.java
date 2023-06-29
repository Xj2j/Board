package ru.xj2j.board.userteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberCreateDto;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberDto;
import ru.xj2j.board.userteamservice.DTO.WorkspaceMemberUpdateDto;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.service.WorkspaceMemberService;

import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{workspaceId}/members")
public class WorkspaceMemberController {

    @Autowired
    private WorkspaceMemberService workSpaceMemberService;

    @GetMapping
    public List<WorkspaceMemberDto> getAllMembers(@PathVariable Long workspaceId) {
        return workSpaceMemberService.getAllMembers(workspaceId);
    }

    @PostMapping
    public ResponseEntity<WorkspaceMemberDto> addMember(@PathVariable Long workspaceId,
                                                        @RequestBody WorkspaceMemberCreateDto memberDto) {
        WorkspaceMemberDto addedMember = workSpaceMemberService.addMember(workspaceId, memberDto);
        if (addedMember != null) {
            return new ResponseEntity<>(addedMember, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<WorkspaceMemberDto> updateMember(@PathVariable Long workspaceId,
                                                           @PathVariable Long memberId,
                                                           @RequestBody WorkspaceMemberUpdateDto memberDto,
                                                           @AuthenticationPrincipal User user) {
        WorkspaceMemberDto updatedMember = workSpaceMemberService.updateMember(workspaceId, memberId, memberDto, user);
        if (updatedMember != null) {
            return new ResponseEntity<>(updatedMember, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long workspaceId,
                                             @PathVariable Long memberId,
                                             @AuthenticationPrincipal User user) {
        boolean deleted = workSpaceMemberService.deleteMember(workspaceId, memberId, user);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
