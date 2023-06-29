package ru.xj2j.board.userteamservice.controller;

import java.util.List;

import jakarta.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.xj2j.board.userteamservice.DTO.WorkspaceDto;
import ru.xj2j.board.userteamservice.service.WorkspaceService;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/{workspaceId}")
    public ResponseEntity<?> getWorkspace(@PathVariable Long workspaceId) {
        try {
            WorkspaceDto workSpace = workspaceService.getWorkspaceById(workspaceId);
            return ResponseEntity.ok(workSpace);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createWorkspace(@RequestBody WorkspaceDto request, Authentication authentication) {
        try {
            WorkspaceDto newWorkSpace = workspaceService.create(request, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(newWorkSpace);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getUserWorkSpaces(Authentication authentication) {
        try {
            List<WorkspaceDto> workSpaces = workspaceService.getUserWorkSpaces(authentication.getName());
            return ResponseEntity.ok(workSpaces);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
